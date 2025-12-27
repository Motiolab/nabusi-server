package com.motiolab.nabusi_server.reservation.application;

import com.motiolab.nabusi_server.centerPackage.center.application.CenterService;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.WellnessLectureReviewService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.WellnessLectureTypeService;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.exception.customException.*;
import com.motiolab.nabusi_server.fcmTokenMobile.application.FcmTokenMobileService;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.memberMemo.application.MemberMemoService;
import com.motiolab.nabusi_server.notificationPackage.notificationFcm.application.NotificationFcmAdminService;
import com.motiolab.nabusi_server.paymentPackage.payment.application.PaymentMobileService;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CreateTossPayRequest;
import com.motiolab.nabusi_server.policy.wellnessClass.application.PolicyWellnessClassService;
import com.motiolab.nabusi_server.policy.wellnessClass.application.dto.PolicyWellnessClassDto;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.application.dto.request.CancelReservationMobileRequestV1;
import com.motiolab.nabusi_server.reservation.application.dto.request.CreateReservationMobileRequestV1;
import com.motiolab.nabusi_server.reservation.application.dto.request.CreateReservationWithPaymentConfirmMobileRequestV1;
import com.motiolab.nabusi_server.reservation.application.dto.request.RefundReservationMobileRequestV1;
import com.motiolab.nabusi_server.reservation.application.dto.request.ValidationReservationBeforePaymentMobileRequestV1;
import com.motiolab.nabusi_server.reservation.application.dto.response.ReservationMobileDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.teacher.application.TeacherService;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.ticketPackage.enums.CntChangedReason;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.WellnessTicketIssuanceHistoryService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.dto.WellnessTicketIssuanceHistoryDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import com.motiolab.nabusi_server.paymentPackage.payment.application.PaymentService;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.TossPayService;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.dto.TossPayDto;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CancelTossPayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log
public class ReservationMobileServiceImpl implements ReservationMobileService {
        private final ReservationService reservationService;
        private final MemberService memberService;
        private final MemberMemoService memberMemoService;
        private final WellnessTicketIssuanceService wellnessTicketIssuanceService;
        private final WellnessLectureService wellnessLectureService;
        private final WellnessTicketIssuanceHistoryService wellnessTicketIssuanceHistoryService;
        private final WellnessTicketManagementService wellnessTicketManagementService;
        private final WellnessLectureTypeService wellnessLectureTypeService;
        private final TeacherService teacherService;
        private final CenterService centerService;
        private final WellnessLectureReviewService wellnessLectureReviewService;
        private final PaymentMobileService paymentMobileService;
        private final FcmTokenMobileService fcmTokenMobileService;
        private final NotificationFcmAdminService notificationFcmAdminService;
        private final PolicyWellnessClassService policyWellnessClassService;
        private final PaymentService paymentService;
        private final TossPayService tossPayService;

        @Transactional
        @Override
        public void createReservation(CreateReservationMobileRequestV1 createReservationMobileRequestV1) {
                this.validateReservation(createReservationMobileRequestV1.getWellnessLectureId(),
                                createReservationMobileRequestV1.getCenterId());

                final ReservationDto reservationDtoExist = reservationService.getByMemberIdAndWellnessLectureId(
                                createReservationMobileRequestV1.getMemberId(),
                                createReservationMobileRequestV1.getWellnessLectureId());
                if (reservationDtoExist != null) {
                        reservationDtoExist.setStatus(createReservationMobileRequestV1.getStatus());
                        reservationService.update(reservationDtoExist);
                }

                final WellnessLectureDto wellnessLectureDto = wellnessLectureService
                                .getById(createReservationMobileRequestV1.getWellnessLectureId());
                WellnessTicketIssuanceDto wellnessTicketIssuanceDto = null;
                if (createReservationMobileRequestV1.getWellnessTicketIssuanceId() != null) {
                        wellnessTicketIssuanceDto = wellnessTicketIssuanceService
                                        .getById(createReservationMobileRequestV1.getWellnessTicketIssuanceId());
                        if (wellnessTicketIssuanceDto == null) {
                                throw new NotFoundException(WellnessTicketIssuanceDto.class,
                                                createReservationMobileRequestV1.getWellnessTicketIssuanceId());
                        }
                        checkWellnessTicketIssuanceToReservation(wellnessTicketIssuanceDto);
                        checkLimitWellnessTicketIssuanceToReservation(wellnessTicketIssuanceDto,
                                        wellnessLectureDto.getStartDateTime());

                        final WellnessTicketManagementDto wellnessTicketManagementDto = wellnessTicketManagementService
                                        .getByWellnessTicketIssuanceId(wellnessTicketIssuanceDto.getId());
                        if (wellnessTicketManagementDto == null) {
                                throw new NotFoundException(WellnessTicketManagementDto.class,
                                                wellnessTicketIssuanceDto.getId());
                        }
                        if (!wellnessLectureDto.getWellnessTicketManagementIdList()
                                        .contains(wellnessTicketManagementDto.getId())) {
                                throw new TicketNotLinkedException(WellnessTicketManagementDto.class,
                                                wellnessTicketIssuanceDto.getId());
                        }
                }

                final ReservationDto reservationDto = ReservationDto.builder()
                                .centerId(createReservationMobileRequestV1.getCenterId())
                                .memberId(createReservationMobileRequestV1.getMemberId())
                                .actionMemberId(createReservationMobileRequestV1.getActionMemberId())
                                .status(createReservationMobileRequestV1.getStatus())
                                .wellnessLectureId(createReservationMobileRequestV1.getWellnessLectureId())
                                .wellnessTicketIssuanceId(
                                                createReservationMobileRequestV1.getWellnessTicketIssuanceId())
                                .build();

                final ReservationDto savedReservationDto = reservationService.create(reservationDto);

                final WellnessTicketIssuanceHistoryDto wellnessTicketIssuanceHistoryDto = WellnessTicketIssuanceHistoryDto
                                .builder()
                                .changedCnt(-1)
                                .wellnessLectureId(createReservationMobileRequestV1.getWellnessLectureId())
                                .reservationId(savedReservationDto.getId())
                                .actionMemberId(createReservationMobileRequestV1.getActionMemberId())
                                .wellnessTicketIssuanceId(
                                                createReservationMobileRequestV1.getWellnessTicketIssuanceId())
                                .reason(CntChangedReason.ADMIN_RESERVATION)
                                .build();
                wellnessTicketIssuanceHistoryService.create(wellnessTicketIssuanceHistoryDto);

                if (wellnessTicketIssuanceDto != null) {
                        wellnessTicketIssuanceDto.setRemainingCnt(wellnessTicketIssuanceDto.getRemainingCnt() - 1);
                        if ((wellnessTicketIssuanceDto.getType().equals("COUNT")
                                        && (wellnessTicketIssuanceDto.getRemainingCnt() <= 0)
                                        || wellnessTicketIssuanceDto.getExpireDate().isBefore(ZonedDateTime.now()))) {
                                wellnessTicketIssuanceDto.setIsDelete(true);
                        }
                        wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
                }
        }

        @Transactional
        @Override
        public void cancelReservation(CancelReservationMobileRequestV1 cancelReservationMobileRequestV1) {
                this.validateCancelReservation(cancelReservationMobileRequestV1.getReservationId());

                final ReservationDto reservationDto = reservationService
                                .getById(cancelReservationMobileRequestV1.getReservationId());
                if (reservationDto == null)
                        throw new NotFoundException(ReservationDto.class,
                                        cancelReservationMobileRequestV1.getReservationId());

                if (!reservationDto.getMemberId().equals(cancelReservationMobileRequestV1.getActionMemberId())) {
                        throw new RuntimeException(
                                        "Member ID mismatch: The provided member ID does not match the reservation's member ID.");
                }

                reservationDto.setStatus(cancelReservationMobileRequestV1.getStatus());
                final ReservationDto updatedReservationDto = reservationService.update(reservationDto);

                final WellnessTicketIssuanceDto wellnessTicketIssuanceDto = wellnessTicketIssuanceService
                                .getById(updatedReservationDto.getWellnessTicketIssuanceId());
                if (wellnessTicketIssuanceDto == null)
                        throw new NotFoundException(WellnessTicketIssuanceDto.class,
                                        updatedReservationDto.getWellnessTicketIssuanceId());

                wellnessTicketIssuanceDto.setRemainingCnt(wellnessTicketIssuanceDto.getRemainingCnt() + 1);
                if ((wellnessTicketIssuanceDto.getType().equals("COUNT")
                                && (wellnessTicketIssuanceDto.getRemainingCnt() <= 0)
                                || wellnessTicketIssuanceDto.getExpireDate().isBefore(ZonedDateTime.now()))) {
                        wellnessTicketIssuanceDto.setIsDelete(true);
                } else {
                        wellnessTicketIssuanceDto.setIsDelete(false);
                }
                wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);

                final WellnessTicketIssuanceHistoryDto wellnessTicketIssuanceHistoryDto = WellnessTicketIssuanceHistoryDto
                                .builder()
                                .changedCnt(1)
                                .wellnessLectureId(updatedReservationDto.getWellnessLectureId())
                                .reservationId(updatedReservationDto.getId())
                                .actionMemberId(cancelReservationMobileRequestV1.getActionMemberId())
                                .wellnessTicketIssuanceId(updatedReservationDto.getWellnessTicketIssuanceId())
                                .reason(CntChangedReason.MEMBER_CANCELED_RESERVATION)
                                .build();
                wellnessTicketIssuanceHistoryService.create(wellnessTicketIssuanceHistoryDto);
        }

        @Override
        public List<ReservationMobileDto> getReservationList(Long memberId) {
                final List<ReservationStatus> reservationStatusList = List.of(ReservationStatus.INAPP_RESERVATION,
                                ReservationStatus.INAPP_PAYMENT_RESERVATION,
                                ReservationStatus.ADMIN_RESERVATION, ReservationStatus.ONSITE_RESERVATION);
                final List<ReservationDto> reservationDtoList = reservationService.getAllByMemberIdAndStatusList(
                                memberId,
                                reservationStatusList);
                final List<Long> wellnessLectureIdList = reservationDtoList.stream()
                                .map(ReservationDto::getWellnessLectureId)
                                .distinct().toList();
                final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService
                                .getAllByIdList(wellnessLectureIdList);
                final List<Long> wellnessLectureTypeIdList = wellnessLectureDtoList.stream()
                                .map(WellnessLectureDto::getWellnessLectureTypeId).distinct().toList();
                final List<WellnessLectureTypeDto> wellnessLectureTypeDtoList = wellnessLectureTypeService
                                .getAllByIdList(wellnessLectureTypeIdList);
                final List<Long> teacherIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getTeacherId)
                                .distinct().toList();
                final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);
                final List<Long> centerIdList = reservationDtoList.stream().map(ReservationDto::getCenterId).distinct()
                                .toList();
                final List<CenterDto> centerDtoList = centerService.getAllByIdList(centerIdList);

                return reservationDtoList
                                .stream()
                                .map(reservationDto -> {
                                        final WellnessLectureDto targetWellnessLectureDto = wellnessLectureDtoList
                                                        .stream()
                                                        .filter(wellnessLectureDto -> Objects.equals(
                                                                        wellnessLectureDto.getId(),
                                                                        reservationDto.getWellnessLectureId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        final WellnessLectureTypeDto targetWellnessLectureTypeDto = wellnessLectureTypeDtoList
                                                        .stream()
                                                        .filter(wellnessLectureTypeDto -> Objects.equals(
                                                                        wellnessLectureTypeDto.id(),
                                                                        targetWellnessLectureDto
                                                                                        .getWellnessLectureTypeId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        final TeacherDto targetTeacherDto = teacherDtoList
                                                        .stream()
                                                        .filter(teacherDto -> Objects.equals(teacherDto.getId(),
                                                                        targetWellnessLectureDto.getTeacherId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        final CenterDto centerDto = centerDtoList
                                                        .stream()
                                                        .filter(center -> Objects.equals(center.getId(),
                                                                        reservationDto.getCenterId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        return ReservationMobileDto
                                                        .builder()
                                                        .reservationDto(reservationDto)
                                                        .wellnessLectureDto(targetWellnessLectureDto)
                                                        .wellnessLectureTypeDto(targetWellnessLectureTypeDto)
                                                        .teacherDto(targetTeacherDto)
                                                        .centerDto(centerDto)
                                                        .build();
                                })
                                .toList();
        }

        @Override
        public List<ReservationMobileDto> getReservationCheckInList(Long memberId) {
                final List<ReservationStatus> reservationStatusList = List.of(ReservationStatus.CHECK_IN);
                final List<ReservationDto> reservationDtoList = reservationService.getAllByMemberIdAndStatusList(
                                memberId,
                                reservationStatusList);
                final List<Long> wellnessLectureIdList = reservationDtoList.stream()
                                .map(ReservationDto::getWellnessLectureId)
                                .distinct().toList();
                final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService
                                .getAllByIdList(wellnessLectureIdList);
                final List<Long> wellnessLectureTypeIdList = wellnessLectureDtoList.stream()
                                .map(WellnessLectureDto::getWellnessLectureTypeId).distinct().toList();
                final List<WellnessLectureTypeDto> wellnessLectureTypeDtoList = wellnessLectureTypeService
                                .getAllByIdList(wellnessLectureTypeIdList);
                final List<Long> teacherIdList = wellnessLectureDtoList.stream().map(WellnessLectureDto::getTeacherId)
                                .distinct().toList();
                final List<TeacherDto> teacherDtoList = teacherService.getAllByIdList(teacherIdList);
                final List<Long> centerIdList = reservationDtoList.stream().map(ReservationDto::getCenterId).distinct()
                                .toList();
                final List<CenterDto> centerDtoList = centerService.getAllByIdList(centerIdList);
                final List<WellnessLectureReviewDto> wellnessLectureReviewDto = wellnessLectureReviewService
                                .getAllByMemberIdAndWellnessLectureIdList(memberId, wellnessLectureIdList);
                final List<WellnessLectureReviewDto> wellnessLectureReviewDtoList = wellnessLectureReviewService
                                .getAllByWellnessLectureIdList(wellnessLectureIdList);

                return reservationDtoList
                                .stream()
                                .map(reservationDto -> {
                                        final WellnessLectureDto targetWellnessLectureDto = wellnessLectureDtoList
                                                        .stream()
                                                        .filter(wellnessLectureDto -> Objects.equals(
                                                                        wellnessLectureDto.getId(),
                                                                        reservationDto.getWellnessLectureId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        final WellnessLectureTypeDto targetWellnessLectureTypeDto = wellnessLectureTypeDtoList
                                                        .stream()
                                                        .filter(wellnessLectureTypeDto -> Objects.equals(
                                                                        wellnessLectureTypeDto.id(),
                                                                        targetWellnessLectureDto
                                                                                        .getWellnessLectureTypeId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        final TeacherDto targetTeacherDto = teacherDtoList
                                                        .stream()
                                                        .filter(teacherDto -> Objects.equals(teacherDto.getId(),
                                                                        targetWellnessLectureDto.getTeacherId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        final CenterDto centerDto = centerDtoList
                                                        .stream()
                                                        .filter(center -> Objects.equals(center.getId(),
                                                                        reservationDto.getCenterId()))
                                                        .findFirst()
                                                        .orElseThrow();

                                        final WellnessLectureReviewDto targetWellnessLectureReviewDto = wellnessLectureReviewDto
                                                        .stream()
                                                        .filter(wellnessLectureReviewDto1 -> Objects.equals(
                                                                        wellnessLectureReviewDto1
                                                                                        .getWellnessLectureId(),
                                                                        reservationDto.getWellnessLectureId()))
                                                        .findFirst()
                                                        .orElse(null);

                                        final List<WellnessLectureReviewDto> targetWellnessLectureReviewDtoList = wellnessLectureReviewDtoList
                                                        .stream()
                                                        .filter(wellnessLectureReviewDto1 -> wellnessLectureReviewDto1
                                                                        .getWellnessClassId()
                                                                        .equals(targetWellnessLectureDto
                                                                                        .getWellnessClassId()))
                                                        .toList();

                                        return ReservationMobileDto
                                                        .builder()
                                                        .reservationDto(reservationDto)
                                                        .wellnessLectureDto(targetWellnessLectureDto)
                                                        .wellnessLectureTypeDto(targetWellnessLectureTypeDto)
                                                        .teacherDto(targetTeacherDto)
                                                        .centerDto(centerDto)
                                                        .myWellnessLectureReviewDto(targetWellnessLectureReviewDto)
                                                        .wellnessLectureReviewDtoList(
                                                                        targetWellnessLectureReviewDtoList)
                                                        .build();
                                })
                                .toList();
        }

        @Transactional
        @Override
        public void createReservationWithPaymentConfirm(CreateReservationWithPaymentConfirmMobileRequestV1 request) {
                this.validateReservation(request.getWellnessLectureId(), request.getCenterId());

                final PaymentDto paymentDto = paymentMobileService
                                .createReservationWithTossPay(CreateTossPayRequest.builder()
                                                .paymentKey(request.getPaymentKey())
                                                .amount(request.getAmount())
                                                .orderId(request.getOrderId())
                                                .memberId(request.getMemberId())
                                                .build());

                if (paymentDto == null) {
                        throw new PaymentFailureException("결제 승인 요청에 실패했습니다.");
                }

                final ReservationDto reservationDto = ReservationDto.builder()
                                .centerId(request.getCenterId())
                                .memberId(request.getMemberId())
                                .actionMemberId(request.getMemberId())
                                .status(ReservationStatus.INAPP_PAYMENT_RESERVATION)
                                .wellnessLectureId(request.getWellnessLectureId())
                                .paymentId(paymentDto.getId())
                                .build();

                reservationService.create(reservationDto);

                final String fcmToken = fcmTokenMobileService.getFcmTokenByMemberId(request.getMemberId());
                if (fcmToken != null) {
                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, "수업 예약 완료",
                                        "수업 예약이 성공적으로 완료되었습니다.");
                }
        }

        @Override
        public void validateReservationBeforePayment(ValidationReservationBeforePaymentMobileRequestV1 request) {
                // 이미 수업에 예약했는지 검사
                final ReservationDto reservationDtoExist = reservationService
                                .getByMemberIdAndWellnessLectureId(request.getMemberId(),
                                                request.getWellnessLectureId());
                if (reservationDtoExist != null
                                && !(reservationDtoExist.getStatus()
                                                .equals(ReservationStatus.MEMBER_CANCELED_RESERVATION)
                                                || reservationDtoExist.getStatus().equals(
                                                                ReservationStatus.MEMBER_CANCELED_RESERVATION_REFUND)
                                                || reservationDtoExist.getStatus().equals(
                                                                ReservationStatus.ADMIN_CANCELED_RESERVATION))) {
                        throw new ExistsAlreadyException(ReservationDto.class, request.getWellnessLectureId());
                }

                // validateReservation 검사 (정책 및 정원)
                final WellnessLectureDto wellnessLectureDto = wellnessLectureService
                                .getById(request.getWellnessLectureId());
                if (wellnessLectureDto == null)
                        throw new NotFoundException(WellnessLectureDto.class, request.getWellnessLectureId());

                this.validateReservation(request.getWellnessLectureId(), wellnessLectureDto.getCenterId());
        }

        @Transactional
        @Override
        public void refundReservation(Long memberId,
                        RefundReservationMobileRequestV1 refundReservationMobileRequestV1) {
                final ReservationDto reservationDto = reservationService
                                .getById(refundReservationMobileRequestV1.getReservationId());
                if (reservationDto == null) {
                        throw new NotFoundException(ReservationDto.class,
                                        refundReservationMobileRequestV1.getReservationId());
                }

                if (!reservationDto.getMemberId().equals(memberId)) {
                        throw new RuntimeException("본인의 예약만 취소할 수 있습니다.");
                }

                if (reservationDto.getPaymentId() == null) {
                        throw new RuntimeException("결제 정보가 없습니다.");
                }

                final PaymentDto paymentDto = paymentService.getById(reservationDto.getPaymentId());

                if (paymentDto.getTossPayId() == null) {
                        throw new RuntimeException("토스페이먼츠 결제 정보가 없습니다.");
                }
                final TossPayDto tossPayDto = tossPayService.getById(paymentDto.getTossPayId());
                paymentMobileService.cancelTossPay(CancelTossPayRequest.builder()
                                .paymentKey(tossPayDto.getPaymentKey())
                                .cancelReason(refundReservationMobileRequestV1.getCancelReason())
                                .reservationId(reservationDto.getId())
                                .build());

                reservationDto.setStatus(ReservationStatus.MEMBER_CANCELED_RESERVATION_REFUND);
                reservationService.update(reservationDto);

                final String fcmToken = fcmTokenMobileService.getFcmTokenByMemberId(memberId);
                if (fcmToken != null) {
                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, "수업 예약 취소 및 환불",
                                        "수업 예약 취소와 환불이 성공적으로 완료되었습니다.");
                }
        }

        private void validateReservation(Long wellnessLectureId, Long centerId) {
                final PolicyWellnessClassDto policyWellnessClassDto = policyWellnessClassService
                                .getByCenterId(centerId);
                if (policyWellnessClassDto == null)
                        throw new NotFoundException(PolicyWellnessClassDto.class, centerId);

                final WellnessLectureDto wellnessLectureDto = wellnessLectureService.getById(wellnessLectureId);
                if (wellnessLectureDto == null)
                        throw new NotFoundException(WellnessLectureDto.class, wellnessLectureId);

                final ZonedDateTime lectureStartDateTime = wellnessLectureDto.getStartDateTime();
                final Integer daysBeforeReservation = policyWellnessClassDto.getReservationStart();
                final Integer minutesAfterReservation = policyWellnessClassDto.getReservationEnd();

                final ZonedDateTime now = ZonedDateTime.now();
                final ZonedDateTime reservationStartTime = lectureStartDateTime.minusDays(daysBeforeReservation);
                final ZonedDateTime reservationEndTime = lectureStartDateTime.plusMinutes(minutesAfterReservation);

                if (now.isBefore(reservationStartTime) || now.isAfter(reservationEndTime)) {
                        throw new ReservationPolicyViolationException("예약 가능한 시간이 아닙니다.");
                }

                checkWellnessLectureToReservation(wellnessLectureDto);
        }

        private void validateCancelReservation(Long reservationId) {
                final ReservationDto reservationDto = reservationService.getById(reservationId);
                if (reservationDto == null)
                        throw new NotFoundException(ReservationDto.class, reservationId);

                final PolicyWellnessClassDto policyWellnessClassDto = policyWellnessClassService
                                .getByCenterId(reservationDto.getCenterId());
                if (policyWellnessClassDto == null)
                        throw new NotFoundException(PolicyWellnessClassDto.class, reservationDto.getCenterId());

                final WellnessLectureDto wellnessLectureDto = wellnessLectureService
                                .getById(reservationDto.getWellnessLectureId());
                if (wellnessLectureDto == null)
                        throw new NotFoundException(WellnessLectureDto.class, reservationDto.getWellnessLectureId());

                final ZonedDateTime lectureStartDateTime = wellnessLectureDto.getStartDateTime();
                final Integer reservationCancelLimit = policyWellnessClassDto.getReservationCancelLimit();

                final ZonedDateTime now = ZonedDateTime.now();
                final ZonedDateTime cancelLimitTime = lectureStartDateTime.minusMinutes(reservationCancelLimit);

                if (now.isAfter(cancelLimitTime)) {
                        throw new ReservationPolicyViolationException("예약 취소 가능 시간이 지났습니다.");
                }
        }

        private void checkLimitWellnessTicketIssuanceToReservation(WellnessTicketIssuanceDto wellnessTicketIssuanceDto,
                        ZonedDateTime lectureStartTime) {
                if (wellnessTicketIssuanceDto.getLimitType().equals("NONE")) {
                        return;
                }

                final List<ReservationDto> reservationDtoList = reservationService
                                .getAllByWellnessTicketIssuanceId(wellnessTicketIssuanceDto.getId())
                                .stream()
                                .filter(reservationDto -> !(reservationDto.getStatus()
                                                .equals(ReservationStatus.MEMBER_CANCELED_RESERVATION)
                                                || reservationDto.getStatus()
                                                                .equals(ReservationStatus.MEMBER_CANCELED_RESERVATION_REFUND)
                                                || reservationDto.getStatus()
                                                                .equals(ReservationStatus.ADMIN_CANCELED_RESERVATION)))
                                .toList();

                final List<Long> wellnessLectureIdList = reservationDtoList.stream()
                                .map(ReservationDto::getWellnessLectureId)
                                .toList();
                final List<WellnessLectureDto> wellnessLectureDtoList = wellnessLectureService
                                .getAllByIdList(wellnessLectureIdList);

                List<ReservationDto> filteredReservations = switch (wellnessTicketIssuanceDto.getLimitType()) {
                        case "WEEK" -> reservationDtoList.stream()
                                        .filter(reservation -> {
                                                final WellnessLectureDto targetWellnessLectureDto = wellnessLectureDtoList
                                                                .stream()
                                                                .filter(wellnessLectureDto -> wellnessLectureDto.getId()
                                                                                .equals(reservation
                                                                                                .getWellnessLectureId()))
                                                                .findFirst()
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error WellnessLecture is Not Available. id:"
                                                                                                + reservation.getWellnessLectureId()));
                                                return isSameWeek(targetWellnessLectureDto.getStartDateTime(),
                                                                lectureStartTime);
                                        }).toList();
                        case "MONTH" -> reservationDtoList.stream()
                                        .filter(reservation -> {
                                                final WellnessLectureDto targetWellnessLectureDto = wellnessLectureDtoList
                                                                .stream()
                                                                .filter(wellnessLectureDto -> wellnessLectureDto.getId()
                                                                                .equals(reservation
                                                                                                .getWellnessLectureId()))
                                                                .findFirst()
                                                                .orElseThrow(() -> new RuntimeException(
                                                                                "Error WellnessLecture is Not Available. id:"
                                                                                                + reservation.getWellnessLectureId()));
                                                return isSameMonth(targetWellnessLectureDto.getStartDateTime(),
                                                                lectureStartTime);
                                        }).toList();
                        default -> List.of();
                };

                if (filteredReservations.size() >= wellnessTicketIssuanceDto.getLimitCnt()) {
                        throw new TicketInvalidException(WellnessTicketIssuanceDto.class,
                                        wellnessTicketIssuanceDto.getId());
                }
        }

        public Boolean isSameMonth(ZonedDateTime startDateTime, ZonedDateTime targetDate) {
                return startDateTime.getYear() == targetDate.getYear() &&
                                startDateTime.getMonthValue() == targetDate.getMonthValue();
        }

        public Boolean isSameWeek(ZonedDateTime startDateTime, ZonedDateTime targetDate) {
                WeekFields weekFields = WeekFields.of(Locale.getDefault());

                int startWeek = startDateTime.get(weekFields.weekOfWeekBasedYear());
                int currentWeek = targetDate.get(weekFields.weekOfWeekBasedYear());

                int startYear = startDateTime.getYear();
                int currentYear = targetDate.getYear();

                return startYear == currentYear && startWeek == currentWeek;
        }

        private void checkWellnessLectureToReservation(WellnessLectureDto wellnessLectureDto) {
                if (wellnessLectureDto.getIsDelete()) {
                        throw new DeletedAlreadyException(WellnessTicketIssuanceDto.class, wellnessLectureDto.getId());
                }

                final List<ReservationDto> reservationDtoList = reservationService
                                .getAllByWellnessLectureId(wellnessLectureDto.getId());
                final int reservationCntWithoutCancel = reservationDtoList.stream()
                                .filter(reservationDto -> !(reservationDto.getStatus()
                                                .equals(ReservationStatus.ADMIN_CANCELED_RESERVATION)
                                                || reservationDto.getStatus()
                                                                .equals(ReservationStatus.MEMBER_CANCELED_RESERVATION)
                                                || reservationDto.getStatus()
                                                                .equals(ReservationStatus.MEMBER_CANCELED_RESERVATION_REFUND)))
                                .toList().size();

                if (reservationCntWithoutCancel >= wellnessLectureDto.getMaxReservationCnt()) {
                        throw new FullReservationException(WellnessLectureDto.class, wellnessLectureDto.getId());
                }
        }

        private void checkWellnessTicketIssuanceToReservation(WellnessTicketIssuanceDto wellnessTicketIssuanceDto) {
                if (wellnessTicketIssuanceDto.getIsDelete()) {
                        throw new DeletedAlreadyException(WellnessTicketIssuanceDto.class,
                                        wellnessTicketIssuanceDto.getId());
                }

                final ZonedDateTime now = ZonedDateTime.now();
                if (wellnessTicketIssuanceDto.getType().equals("COUNT")
                                && (wellnessTicketIssuanceDto.getRemainingCnt() <= 0
                                                || wellnessTicketIssuanceDto.getExpireDate().isBefore(now))) {
                        wellnessTicketIssuanceDto.setIsDelete(true);
                        wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
                        throw new TicketInvalidException(WellnessTicketIssuanceDto.class,
                                        wellnessTicketIssuanceDto.getId());
                }
                if (wellnessTicketIssuanceDto.getType().equals("PERIOD")
                                && wellnessTicketIssuanceDto.getExpireDate().isBefore(now)) {
                        wellnessTicketIssuanceDto.setIsDelete(true);
                        wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
                        throw new TicketInvalidException(WellnessTicketIssuanceDto.class,
                                        wellnessTicketIssuanceDto.getId());
                }
        }
}
