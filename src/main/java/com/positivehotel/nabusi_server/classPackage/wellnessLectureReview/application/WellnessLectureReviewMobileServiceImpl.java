package com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterRoomDto;
import com.positivehotel.nabusi_server.classPackage.wellnessClass.application.WellnessClassService;
import com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewMobileDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.CreateWellnessLectureReviewMobileRequest;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.UpdateWellnessLectureReviewMobileRequest;
import com.positivehotel.nabusi_server.exception.customException.ExistsAlreadyException;
import com.positivehotel.nabusi_server.exception.customException.NotFoundException;
import com.positivehotel.nabusi_server.exception.customException.NotMemberException;
import com.positivehotel.nabusi_server.memberPackage.member.application.MemberService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.reservation.application.ReservationService;
import com.positivehotel.nabusi_server.reservation.application.dto.ReservationDto;
import com.positivehotel.nabusi_server.reservation.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WellnessLectureReviewMobileServiceImpl implements WellnessLectureReviewMobileService{
    private final WellnessLectureReviewService wellnessLectureReviewService;
    private final WellnessLectureService wellnessLectureService;
    private final WellnessClassService wellnessClassService;
    private final ReservationService reservationService;
    private final MemberService memberService;

    @Override
    public void createWellnessLectureReview(Long memberId, CreateWellnessLectureReviewMobileRequest createWellnessLectureReviewMobileRequest) {
        final WellnessLectureReviewDto wellnessLectureReviewDto = wellnessLectureReviewService.getByMemberIdAndWellnessLectureId(memberId, createWellnessLectureReviewMobileRequest.getWellnessLectureId());
        if(wellnessLectureReviewDto != null) {
            throw new ExistsAlreadyException(CenterRoomDto.class, wellnessLectureReviewDto.getId());
        }

        final WellnessLectureDto wellnessLectureDto = wellnessLectureService.getById(createWellnessLectureReviewMobileRequest.getWellnessLectureId());
        if(wellnessLectureDto == null) {
            throw new NotFoundException(WellnessLectureDto.class, createWellnessLectureReviewMobileRequest.getWellnessLectureId());
        }

        final WellnessClassDto wellnessClassDto = wellnessClassService.getById(wellnessLectureDto.getWellnessClassId());
        if(wellnessClassDto == null) {
            throw new NotFoundException(WellnessClassDto.class, wellnessLectureDto.getWellnessClassId());
        }

        final ReservationDto reservationDto = reservationService.getByMemberIdAndWellnessLectureId(memberId, createWellnessLectureReviewMobileRequest.getWellnessLectureId());
        if(reservationDto == null) {
            throw new NotMemberException(ReservationDto.class, createWellnessLectureReviewMobileRequest.getWellnessLectureId());
        }

        final WellnessLectureReviewDto newWellnessLectureReviewDto = WellnessLectureReviewDto.builder()
                .rating(createWellnessLectureReviewMobileRequest.getRating())
                .wellnessLectureId(wellnessLectureDto.getId())
                .wellnessClassId(wellnessClassDto.getId())
                .memberId(memberId)
                .teacherId(wellnessLectureDto.getTeacherId())
                .centerId(wellnessClassDto.getCenterId())
                .content(createWellnessLectureReviewMobileRequest.getContent())
                .isPrivate(createWellnessLectureReviewMobileRequest.getIsPrivate())
                .isDelete(false)
                .build();

        wellnessLectureReviewService.create(newWellnessLectureReviewDto);
    }

    @Override
    public WellnessLectureReviewMobileDto getWellnessLectureReviewById(Long wellnessLectureReviewId) {
        final WellnessLectureReviewDto wellnessLectureReviewDto = wellnessLectureReviewService.getById(wellnessLectureReviewId);
        if(wellnessLectureReviewDto == null) {
            throw new NotFoundException(WellnessLectureReviewDto.class, wellnessLectureReviewId);
        }
        return WellnessLectureReviewMobileDto.builder()
                .wellnessLectureReviewDto(wellnessLectureReviewDto)
                .build();
    }

    @Override
    public void updateWellnessLectureReview(Long memberId, UpdateWellnessLectureReviewMobileRequest updateWellnessLectureReviewMobileRequest) {
        final WellnessLectureReviewDto wellnessLectureReviewDto = wellnessLectureReviewService.getById(updateWellnessLectureReviewMobileRequest.getWellnessLectureReviewId());

        if(!Objects.equals(wellnessLectureReviewDto.getMemberId(), memberId)) {
            throw new NotMemberException(WellnessLectureReviewDto.class, updateWellnessLectureReviewMobileRequest.getWellnessLectureReviewId());
        }

        wellnessLectureReviewDto.setContent(updateWellnessLectureReviewMobileRequest.getContent());
        wellnessLectureReviewDto.setRating(updateWellnessLectureReviewMobileRequest.getRating());
        wellnessLectureReviewDto.setIsPrivate(updateWellnessLectureReviewMobileRequest.getIsPrivate());

        wellnessLectureReviewService.update(wellnessLectureReviewDto);
    }

    @Override
    public List<WellnessLectureReviewMobileDto> getWellnessLectureReviewListByTypeAndId(String type, Long id) {
        if(type.equals("wellness_class")) {
            final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByWellnessClassId(id);
            final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList.stream().filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete()).toList();
            return getWellnessLectureReviewMobileDtos(wellnessLectureReviewIsDelteFalseDtoList);
        }else if (type.equals("wellness_lecture")) {
            final WellnessLectureDto wellnessLectureDto = wellnessLectureService.getById(id);
            final Long wellnessClassId = wellnessLectureDto.getWellnessClassId();
            final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByWellnessClassId(wellnessClassId);
            final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList.stream().filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete()).toList();
            return getWellnessLectureReviewMobileDtos(wellnessLectureReviewIsDelteFalseDtoList);
        }else if (type.equals("center")) {
            final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByCenterId(id);
            final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList.stream().filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete()).toList();
            return getWellnessLectureReviewMobileDtos(wellnessLectureReviewIsDelteFalseDtoList);
        }else if (type.equals("teacher")) {
            final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByTeacherId(id);
            final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList.stream().filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete()).toList();
            return getWellnessLectureReviewMobileDtos(wellnessLectureReviewIsDelteFalseDtoList);
        }

        return List.of();
    }

    private List<WellnessLectureReviewMobileDto> getWellnessLectureReviewMobileDtos(List<WellnessLectureReviewDto> wellnessLectureReviewDtoList) {
        final List<Long> memberIdList = wellnessLectureReviewDtoList.stream().map(WellnessLectureReviewDto::getMemberId).distinct().toList();
        final List<MemberDto> memberDtoList = memberService.getAllByIdList(memberIdList);
        final List<WellnessLectureReviewDto> memberWellnessLectureReviewDtoList = wellnessLectureReviewService.getAllByMemberIdList(memberIdList);
        final List<ReservationStatus> reservationStatusList = List.of(ReservationStatus.CHECK_IN);
        final List<ReservationDto> memberReservationDtoList = reservationService.getAllByMemberIdListAndStatusList(memberIdList, reservationStatusList);

        return wellnessLectureReviewDtoList
                .stream()
                .map(wellnessLectureReviewDto -> {
                    final MemberDto targetMemberDto = memberDtoList
                            .stream()
                            .filter(memberDto -> Objects.equals(memberDto.getId(), wellnessLectureReviewDto.getMemberId()))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException(MemberDto.class, wellnessLectureReviewDto.getMemberId()));

                    final List<WellnessLectureReviewDto> targetWellnessLectureReviewDtoList = memberWellnessLectureReviewDtoList
                            .stream()
                            .filter(memberWellnessLectureReviewDto -> Objects.equals(memberWellnessLectureReviewDto.getMemberId(), wellnessLectureReviewDto.getMemberId()))
                            .toList();

                    final List<ReservationDto> targetReservationDtoList = memberReservationDtoList
                            .stream()
                            .filter(memberReservationDto -> Objects.equals(memberReservationDto.getMemberId(), wellnessLectureReviewDto.getMemberId()))
                            .toList();

                    return WellnessLectureReviewMobileDto.builder()
                            .wellnessLectureReviewDto(wellnessLectureReviewDto)
                            .memberDtoExtension(
                                    WellnessLectureReviewMobileDto.MemberDtoExtension.builder()
                                            .memberDto(targetMemberDto)
                                            .wellnessLectureReviewDtoList(targetWellnessLectureReviewDtoList)
                                            .reservationDtoList(targetReservationDtoList)
                                            .build()
                            )
                            .build();
                }).toList();
    }
}
