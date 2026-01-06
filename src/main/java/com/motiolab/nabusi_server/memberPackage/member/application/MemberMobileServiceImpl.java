package com.motiolab.nabusi_server.memberPackage.member.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.WellnessClassService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewService;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.GetHomeSummaryMobileResponse;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.GetMemberMyInfoMobileResponse;
import com.motiolab.nabusi_server.memberPackage.memberPoint.application.MemberPointService;
import com.motiolab.nabusi_server.memberPackage.memberPoint.application.dto.MemberPointDto;
import com.motiolab.nabusi_server.reservation.application.ReservationService;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.socialUser.appleUser.application.AppleUserService;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleUserDto;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.KakaoUserMobileService;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.KakaoUserService;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserDto;
import com.motiolab.nabusi_server.socialUser.naverToken.application.NaverTokenService;
import com.motiolab.nabusi_server.socialUser.naverToken.application.dto.NaverTokenDto;
import com.motiolab.nabusi_server.socialUser.naverUser.application.NaverUserMobileService;
import com.motiolab.nabusi_server.socialUser.naverUser.application.NaverUserService;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.NaverUserDto;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberMobileServiceImpl implements MemberMobileService {

    private final MemberService memberService;
    private final NaverUserService naverUserService;
    private final KakaoUserMobileService kakaoUserMobileService;
    private final KakaoUserService kakaoUserService;
    private final AppleUserService appleUserService;
    private final NaverUserMobileService naverUserMobileService;
    private final NaverTokenService naverTokenService;
    private final ReservationService reservationService;
    private final WellnessLectureReviewService wellnessLectureReviewService;
    private final MemberPointService memberPointService;
    private final WellnessClassService wellnessClassService;
    private final TeacherService teacherService;

    @Override
    public GetHomeSummaryMobileResponse getHomeSummary(Long memberId) {
        long attendanceCount = reservationService.countByMemberIdAndStatus(memberId, ReservationStatus.CHECK_IN);

        List<ReservationStatus> activeStatusList = List.of(
                ReservationStatus.INAPP_RESERVATION,
                ReservationStatus.INAPP_PAYMENT_RESERVATION,
                ReservationStatus.ADMIN_RESERVATION,
                ReservationStatus.ONSITE_RESERVATION);
        long reservationCount = reservationService.countByMemberIdAndStatusIn(memberId, activeStatusList);

        long reviewCount = wellnessLectureReviewService.countByMemberId(memberId);

        return GetHomeSummaryMobileResponse.builder()
                .attendanceCount((int) attendanceCount)
                .reservationCount((int) reservationCount)
                .reviewCount((int) reviewCount)
                .build();
    }

    @Override
    public GetMemberMyInfoMobileResponse getMyInfo(Long memberId) {
        MemberDto memberDto = memberService.getById(memberId);
        if (memberDto == null) {
            throw new NotFoundException(MemberDto.class, memberId);
        }

        GetMemberMyInfoMobileResponse.GetMemberMyInfoMobileResponseBuilder builder = GetMemberMyInfoMobileResponse
                .builder()
                .memberName(memberDto.getName())
                .memberEmail(memberDto.getEmail())
                .socialName(memberDto.getSocialName());

        // 소셜 정보 (Social Info) 채우기
        switch (memberDto.getSocialName()) {
            case "kakao" -> {
                KakaoUserDto kakaoUserDto = kakaoUserService.getByMemberId(memberId);
                builder.birthday(kakaoUserDto.getBirthDay())
                        .birthYear(kakaoUserDto.getBirthYear())
                        .gender(kakaoUserDto.getGender())
                        .phoneNumber(kakaoUserDto.getPhoneNumber());
            }
            case "naver" -> {
                NaverUserDto naverUserDto = naverUserService.getByMemberId(memberId);
                builder.birthday(naverUserDto.getBirthday())
                        .birthYear(naverUserDto.getBirthyear())
                        .gender(naverUserDto.getGender())
                        .phoneNumber(naverUserDto.getMobile());
            }
            case "apple" -> {
                AppleUserDto appleUserDto = appleUserService.getByMemberId(memberId);
                builder.phoneNumber(appleUserDto.getMobile());
            }
        }

        // 포인트 정보
        long totalPoints = Optional.ofNullable(memberPointService.getByMemberId(memberId))
                .map(MemberPointDto::getPoint)
                .orElse(0L);
        builder.totalPoints(totalPoints);

        // 강사 여부 확인 (최적화: 전체 강사가 아닌 해당 회원의 강사 정보만 매칭)
        List<Long> teacherIdsWithClasses = wellnessClassService.getDistinctTeacherIds();
        boolean isTeacherOfClass = teacherService.getAllByMemberId(memberId).stream()
                .anyMatch(teacher -> teacherIdsWithClasses.contains(teacher.getId()));
        builder.isExistClass(isTeacherOfClass);

        return builder.build();
    }

    @Override
    public Boolean delete(Long memberId) {
        MemberDto memberDto = memberService.getById(memberId);
        if (memberDto == null)
            throw new NotFoundException(MemberDto.class, memberId);

        switch (memberDto.getSocialName()) {
            case "kakao" -> {
                String randomValue = generateRandomNumber();
                String resignedSocialId = kakaoUserMobileService.delete(memberId);
                KakaoUserDto kakaoUserDto = kakaoUserService.getByMemberId(memberId);
                kakaoUserDto.setPhoneNumber(convertToKorean(kakaoUserDto.getPhoneNumber()) + "-" + randomValue);
                kakaoUserService.patch(kakaoUserDto);
                String mobilePrefix = convertToKorean(memberDto.getMobile());
                memberService.updateMobile(memberId, mobilePrefix + "-" + generateRandomNumber());
                memberService.delete(memberId);
            }
            case "naver" -> {
                String randomValue = generateRandomNumber();
                NaverTokenDto naverTokenDto = naverTokenService.getFirstByMemberIdOrderByCreatedDateDesc(memberId);
                if (naverTokenDto == null)
                    throw new NotFoundException(NaverTokenDto.class, memberId);
                naverUserMobileService.resignNaverUser(naverTokenDto.getAccessToken());
                NaverUserDto naverUserDto = naverUserService.getByMemberId(memberId);
                naverUserDto.setMobileE164(convertToKorean(naverUserDto.getMobileE164()) + "-" + randomValue);
                naverUserDto.setMobile(convertToKorean(naverUserDto.getMobile()) + "-" + randomValue);
                naverUserService.patchById(naverUserDto.getId(), naverUserDto);
                String mobilePrefix = convertToKorean(memberDto.getMobile());
                memberService.updateMobile(memberId, mobilePrefix + "-" + randomValue);
                memberService.delete(memberId);
            }
            case "apple" -> {
                String randomValue = generateRandomNumber();
                AppleUserDto appleUserDto = appleUserService.getByMemberId(memberId);
                appleUserDto.setMobile(convertToKorean(appleUserDto.getMobile()) + "-" + randomValue);
                appleUserDto.setSub("");
                appleUserService.patchById(appleUserDto.getId(), appleUserDto);
                String mobilePrefix = convertToKorean(memberDto.getMobile());
                memberService.updateMobile(memberId, mobilePrefix + "-" + randomValue);
                memberService.delete(memberId);
            }
        }
        return true;
    }

    private String convertToKorean(String number) {
        List<String> NUMBERS = new ArrayList<>(Arrays.asList("공", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"));
        if (!NUMBERS.stream().filter(number::contains).toList().isEmpty()) {
            return number.length() > 12 ? number.substring(0, 11) : number;
        }

        StringBuilder result = new StringBuilder();

        for (char digit : number.toCharArray()) {
            int index = Character.getNumericValue(digit);
            if (index >= 0 && index < NUMBERS.size()) {
                result.append(NUMBERS.get(index));
            } else if (digit == '+') {
                result.append('+');
            } else if (digit == '-') {
                result.append('-');
            } else if (digit == ' ') {
                result.append(' ');
            } else {
                throw new IllegalArgumentException("Invalid character in input: " + digit);
            }
        }

        return result.toString();
    }

    private String generateRandomNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // 0부터 9까지의 무작위 숫자 생성
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}
