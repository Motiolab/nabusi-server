package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application;

import com.motiolab.nabusi_server.centerPackage.center.application.CenterService;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterRoomDto;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.WellnessClassService;
import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.CreateWellnessLectureReviewMobileRequest;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.UpdateWellnessLectureReviewMobileRequest;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.WellnessLectureReviewCommentService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.WellnessLectureReviewCommentDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.request.CreateWellnessLectureReviewCommentMobileRequest;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.request.DeleteWellnessLectureReviewCommentMobileRequest;
import com.motiolab.nabusi_server.exception.customException.ExistsAlreadyException;
import com.motiolab.nabusi_server.exception.customException.NoAuthorityException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.exception.customException.NotMemberException;
import com.motiolab.nabusi_server.fcmTokenMobile.application.FcmTokenMobileService;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.notificationPackage.notificationFcm.application.NotificationFcmAdminService;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.FcmNotificationHistoryService;
import com.motiolab.nabusi_server.reservation.application.ReservationService;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class WellnessLectureReviewMobileServiceImpl implements WellnessLectureReviewMobileService {
        private final WellnessLectureReviewService wellnessLectureReviewService;
        private final WellnessLectureService wellnessLectureService;
        private final WellnessClassService wellnessClassService;
        private final ReservationService reservationService;
        private final MemberService memberService;
        private final TeacherService teacherService;
        private final CenterService centerService;
        private final WellnessLectureReviewCommentService wellnessLectureReviewCommentService;
        private final FcmTokenMobileService fcmTokenMobileService;
        private final NotificationFcmAdminService notificationFcmAdminService;
        private final FcmNotificationHistoryService fcmNotificationHistoryService;

        @Transactional
        @Override
        public void createWellnessLectureReview(Long memberId,
                        CreateWellnessLectureReviewMobileRequest createWellnessLectureReviewMobileRequest) {
                final WellnessLectureReviewDto wellnessLectureReviewDto = wellnessLectureReviewService
                                .getByMemberIdAndWellnessLectureId(memberId,
                                                createWellnessLectureReviewMobileRequest.getWellnessLectureId());
                if (wellnessLectureReviewDto != null) {
                        throw new ExistsAlreadyException(CenterRoomDto.class, wellnessLectureReviewDto.getId());
                }

                final WellnessLectureDto wellnessLectureDto = wellnessLectureService
                                .getById(createWellnessLectureReviewMobileRequest.getWellnessLectureId());
                if (wellnessLectureDto == null) {
                        throw new NotFoundException(WellnessLectureDto.class,
                                        createWellnessLectureReviewMobileRequest.getWellnessLectureId());
                }

                final WellnessClassDto wellnessClassDto = wellnessClassService
                                .getById(wellnessLectureDto.getWellnessClassId());
                if (wellnessClassDto == null) {
                        throw new NotFoundException(WellnessClassDto.class, wellnessLectureDto.getWellnessClassId());
                }

                final ReservationDto reservationDto = reservationService.getByMemberIdAndWellnessLectureId(memberId,
                                createWellnessLectureReviewMobileRequest.getWellnessLectureId());
                if (reservationDto == null) {
                        throw new NotMemberException(ReservationDto.class,
                                        createWellnessLectureReviewMobileRequest.getWellnessLectureId());
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

                final WellnessLectureReviewDto savedReview = wellnessLectureReviewService
                                .create(newWellnessLectureReviewDto);

                final String fcmToken = fcmTokenMobileService.getFcmTokenByMemberId(memberId);
                if (fcmToken != null) {
                        final TeacherDto teacher = teacherService.getById(wellnessLectureDto.getTeacherId());
                        final CenterDto center = centerService.getById(wellnessClassDto.getCenterId());

                        final String title = "리뷰 등록 완료";
                        final String body = "리뷰 등록이 성공적으로 완료되었습니다.";
                        final String detail = "[리뷰 등록 정보]\n" +
                                        "리뷰 내용: " + newWellnessLectureReviewDto.getContent() +
                                        "별점: " + newWellnessLectureReviewDto.getRating() +
                                        "\n" +
                                        "수업 이름: " + wellnessLectureDto.getName() + "\n" +
                                        "선생님 이름: " + teacher.getName() + "\n" +
                                        "센터 이름: " + center.getName() + "\n";
                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, title, body);
                        fcmNotificationHistoryService.save(memberId, title, body, detail);

                        final Long teacherMemberId = teacher.getMemberId();
                        if (!Objects.equals(teacherMemberId, memberId)) {
                                final String fcmTokenTeacher = fcmTokenMobileService
                                                .getFcmTokenByMemberId(teacherMemberId);
                                if (fcmTokenTeacher != null) {
                                        notificationFcmAdminService.sendNotificationFcmTest(fcmTokenTeacher, title,
                                                        body);
                                        fcmNotificationHistoryService.save(teacherMemberId, title, body, detail);
                                }
                        }
                }
        }

        @Override
        public WellnessLectureReviewMobileDto getWellnessLectureReviewById(Long wellnessLectureReviewId) {
                final WellnessLectureReviewDto wellnessLectureReviewDto = wellnessLectureReviewService
                                .getById(wellnessLectureReviewId);
                if (wellnessLectureReviewDto == null) {
                        throw new NotFoundException(WellnessLectureReviewDto.class, wellnessLectureReviewId);
                }
                return WellnessLectureReviewMobileDto.builder()
                                .wellnessLectureReviewDto(wellnessLectureReviewDto)
                                .build();
        }

        @Override
        public void updateWellnessLectureReview(Long memberId,
                        UpdateWellnessLectureReviewMobileRequest updateWellnessLectureReviewMobileRequest) {
                final WellnessLectureReviewDto wellnessLectureReviewDto = wellnessLectureReviewService
                                .getById(updateWellnessLectureReviewMobileRequest.getWellnessLectureReviewId());

                if (!Objects.equals(wellnessLectureReviewDto.getMemberId(), memberId)) {
                        throw new NotMemberException(WellnessLectureReviewDto.class,
                                        updateWellnessLectureReviewMobileRequest.getWellnessLectureReviewId());
                }

                wellnessLectureReviewDto.setContent(updateWellnessLectureReviewMobileRequest.getContent());
                wellnessLectureReviewDto.setRating(updateWellnessLectureReviewMobileRequest.getRating());
                wellnessLectureReviewDto.setIsPrivate(updateWellnessLectureReviewMobileRequest.getIsPrivate());

                wellnessLectureReviewService.update(wellnessLectureReviewDto);
        }

        @Override
        public List<WellnessLectureReviewMobileDto> getWellnessLectureReviewListByTypeAndId(Long memberId, String type,
                        Long id) {
                if (type.equals("wellness_class")) {
                        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService
                                        .getAllByWellnessClassId(id);
                        final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList
                                        .stream()
                                        .filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete())
                                        .toList();
                        return getWellnessLectureReviewMobileDtos(memberId, wellnessLectureReviewIsDelteFalseDtoList);
                } else if (type.equals("wellness_lecture")) {
                        final WellnessLectureDto wellnessLectureDto = wellnessLectureService.getById(id);
                        final Long wellnessClassId = wellnessLectureDto.getWellnessClassId();
                        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService
                                        .getAllByWellnessClassId(wellnessClassId);
                        final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList
                                        .stream()
                                        .filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete())
                                        .toList();
                        return getWellnessLectureReviewMobileDtos(memberId, wellnessLectureReviewIsDelteFalseDtoList);
                } else if (type.equals("center")) {
                        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService
                                        .getAllByCenterId(id);
                        final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList
                                        .stream()
                                        .filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete())
                                        .toList();
                        return getWellnessLectureReviewMobileDtos(memberId, wellnessLectureReviewIsDelteFalseDtoList);
                } else if (type.equals("teacher")) {
                        final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService
                                        .getAllByTeacherId(id);
                        final List<WellnessLectureReviewDto> wellnessLectureReviewIsDelteFalseDtoList = wellnessLectureReviewDtoList
                                        .stream()
                                        .filter(wellnessLectureReviewDto -> !wellnessLectureReviewDto.getIsDelete())
                                        .toList();
                        return getWellnessLectureReviewMobileDtos(memberId, wellnessLectureReviewIsDelteFalseDtoList);
                }

                return List.of();
        }

        private List<WellnessLectureReviewMobileDto> getWellnessLectureReviewMobileDtos(Long memberId,
                        List<WellnessLectureReviewDto> wellnessLectureReviewDtoList) {
                final List<Long> memberIdList = wellnessLectureReviewDtoList.stream()
                                .map(WellnessLectureReviewDto::getMemberId)
                                .distinct().toList();
                final List<MemberDto> memberDtoList = memberService.getAllByIdList(memberIdList);
                final List<WellnessLectureReviewDto> memberWellnessLectureReviewDtoList = wellnessLectureReviewService
                                .getAllByMemberIdList(memberIdList);
                final List<ReservationStatus> reservationStatusList = List.of(ReservationStatus.CHECK_IN);
                final List<ReservationDto> memberReservationDtoList = reservationService
                                .getAllByMemberIdListAndStatusList(memberIdList, reservationStatusList);

                final List<Long> teacherIdList = wellnessLectureReviewDtoList.stream()
                                .map(WellnessLectureReviewDto::getTeacherId).distinct().toList();

                final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);

                return wellnessLectureReviewDtoList
                                .stream()
                                .map(wellnessLectureReviewDto -> {
                                        final MemberDto targetMemberDto = memberDtoList
                                                        .stream()
                                                        .filter(memberDto -> Objects.equals(memberDto.getId(),
                                                                        wellnessLectureReviewDto.getMemberId()))
                                                        .findFirst()
                                                        .orElseThrow(() -> new NotFoundException(MemberDto.class,
                                                                        wellnessLectureReviewDto.getMemberId()));

                                        final List<WellnessLectureReviewDto> targetWellnessLectureReviewDtoList = memberWellnessLectureReviewDtoList
                                                        .stream()
                                                        .filter(memberWellnessLectureReviewDto -> Objects.equals(
                                                                        memberWellnessLectureReviewDto.getMemberId(),
                                                                        wellnessLectureReviewDto.getMemberId()))
                                                        .toList();

                                        final List<ReservationDto> targetReservationDtoList = memberReservationDtoList
                                                        .stream()
                                                        .filter(memberReservationDto -> Objects.equals(
                                                                        memberReservationDto.getMemberId(),
                                                                        wellnessLectureReviewDto.getMemberId()))
                                                        .toList();

                                        final TeacherDto targetTeacherDto = teacherDtoList
                                                        .stream()
                                                        .filter(teacherDto -> Objects.equals(teacherDto.getId(),
                                                                        wellnessLectureReviewDto.getTeacherId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        final Boolean isCreateCommentAvailable = Objects
                                                        .equals(Objects.requireNonNull(targetTeacherDto).getMemberId(),
                                                                        memberId)
                                                        || Objects.equals(targetMemberDto.getId(), memberId);

                                        return WellnessLectureReviewMobileDto.builder()
                                                        .wellnessLectureReviewDto(wellnessLectureReviewDto)
                                                        .isCreateCommentAvailable(isCreateCommentAvailable)
                                                        .wellnessLectureReviewCommentDtoList(
                                                                        wellnessLectureReviewCommentService
                                                                                        .getAllByWellnessLectureReviewId(
                                                                                                        wellnessLectureReviewDto
                                                                                                                        .getId()))
                                                        .memberDtoExtension(
                                                                        WellnessLectureReviewMobileDto.MemberDtoExtension
                                                                                        .builder()
                                                                                        .memberDto(targetMemberDto)
                                                                                        .wellnessLectureReviewDtoList(
                                                                                                        targetWellnessLectureReviewDtoList)
                                                                                        .reservationDtoList(
                                                                                                        targetReservationDtoList)
                                                                                        .build())
                                                        .build();
                                }).toList();
        }

        @Override
        public void createComment(Long memberId,
                        CreateWellnessLectureReviewCommentMobileRequest createWellnessLectureReviewCommentMobileRequest) {
                final WellnessLectureReviewDto wellnessLectureReviewDto = wellnessLectureReviewService
                                .getById(createWellnessLectureReviewCommentMobileRequest.getWellnessLectureReviewId());
                final TeacherDto teacherDto = teacherService.getById(wellnessLectureReviewDto.getTeacherId());

                final boolean isCreateCommentAvailable = Objects.equals(teacherDto.getMemberId(), memberId)
                                || Objects.equals(wellnessLectureReviewDto.getMemberId(), memberId);

                if (!isCreateCommentAvailable) {
                        throw new NoAuthorityException(WellnessLectureReviewDto.class,
                                        createWellnessLectureReviewCommentMobileRequest.getWellnessLectureReviewId());
                }

                wellnessLectureReviewCommentService.create(
                                createWellnessLectureReviewCommentMobileRequest.getWellnessLectureReviewId(), memberId,
                                createWellnessLectureReviewCommentMobileRequest.getContent());

                final String fcmToken = fcmTokenMobileService.getFcmTokenByMemberId(memberId);
                if (fcmToken != null) {
                        final WellnessLectureDto lecture = wellnessLectureService
                                        .getById(wellnessLectureReviewDto.getWellnessLectureId());
                        final CenterDto center = centerService.getById(wellnessLectureReviewDto.getCenterId());

                        final String title = "답변 등록 완료";
                        final String body = "답변 등록이 성공적으로 완료되었습니다.";
                        final String detail = "[리뷰 답변 정보]\n" +
                                        "답변 내용: " + createWellnessLectureReviewCommentMobileRequest.getContent() +
                                        "\n" +
                                        "[수업 & 리뷰 정보]\n" +
                                        "수업 이름: " + lecture.getName() + "\n" +
                                        "선생님 이름: " + teacherDto.getName() + "\n" +
                                        "센터 이름: " + center.getName() + "\n" +
                                        "리뷰 내용: " + wellnessLectureReviewDto.getContent() + "\n";

                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, title, body);
                        fcmNotificationHistoryService.save(memberId, title, body, detail);

                        final Long reviewMemberId = wellnessLectureReviewDto.getMemberId();
                        if (!Objects.equals(reviewMemberId, memberId)) {
                                final String fcmTokenReviewMemberId = fcmTokenMobileService
                                                .getFcmTokenByMemberId(reviewMemberId);
                                if (fcmTokenReviewMemberId != null) {
                                        notificationFcmAdminService.sendNotificationFcmTest(fcmTokenReviewMemberId,
                                                        title, body);
                                        fcmNotificationHistoryService.save(reviewMemberId, title, body, detail);
                                }
                        }
                }
        }

        @Override
        public void deleteComment(Long memberId,
                        DeleteWellnessLectureReviewCommentMobileRequest deleteWellnessLectureReviewCommentMobileRequest) {
                final WellnessLectureReviewCommentDto commentDto = wellnessLectureReviewCommentService
                                .getById(deleteWellnessLectureReviewCommentMobileRequest
                                                .getWellnessLectureReviewCommentId());

                final WellnessLectureReviewDto reviewDto = wellnessLectureReviewService
                                .getById(commentDto.getWellnessLectureReviewId());
                final TeacherDto teacherDto = teacherService.getById(reviewDto.getTeacherId());
                final WellnessLectureDto lecture = wellnessLectureService.getById(reviewDto.getWellnessLectureId());
                final CenterDto center = centerService.getById(reviewDto.getCenterId());

                wellnessLectureReviewCommentService.delete(
                                deleteWellnessLectureReviewCommentMobileRequest.getWellnessLectureReviewCommentId(),
                                memberId);

                final String fcmToken = fcmTokenMobileService.getFcmTokenByMemberId(memberId);
                if (fcmToken != null) {
                        final String title = "답변 삭제 완료";
                        final String body = "답변 삭제가 성공적으로 완료되었습니다.";
                        final String detail = "[리뷰 답변 삭제 정보]\n" +
                                        "삭제된 답변 내용: " + commentDto.getContent() + "\n" +
                                        "\n" +
                                        "[수업 & 리뷰 정보]\n" +
                                        "수업 이름: " + lecture.getName() + "\n" +
                                        "선생님 이름: " + teacherDto.getName() + "\n" +
                                        "센터 이름: " + center.getName() + "\n" +
                                        "리뷰 내용: " + reviewDto.getContent() + "\n";

                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, title, body);
                        fcmNotificationHistoryService.save(memberId, title, body, detail);

                        final Long reviewMemberId = reviewDto.getMemberId();
                        if (!Objects.equals(reviewMemberId, memberId)) {
                                final String fcmTokenReviewMemberId = fcmTokenMobileService
                                                .getFcmTokenByMemberId(reviewMemberId);
                                if (fcmTokenReviewMemberId != null) {
                                        notificationFcmAdminService.sendNotificationFcmTest(fcmTokenReviewMemberId,
                                                        title, body);
                                        fcmNotificationHistoryService.save(reviewMemberId, title, body, detail);
                                }
                        }
                }
        }
}
