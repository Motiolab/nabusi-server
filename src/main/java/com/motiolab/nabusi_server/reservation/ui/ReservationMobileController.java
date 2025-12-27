package com.motiolab.nabusi_server.reservation.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.reservation.application.ReservationMobileService;
import com.motiolab.nabusi_server.reservation.application.dto.request.*;
import com.motiolab.nabusi_server.reservation.application.dto.response.*;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderMobileDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CreateOrderWithPaymentConfirmMobileRequestV1;
import com.motiolab.nabusi_server.util.WellnessLectureReviewUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationMobileController {
    private final ReservationMobileService reservationMobileService;

    @PostMapping("/v1/mobile/reservation/create")
    public ResponseEntity<CreateReservationAdminResponseV1> createReservation(@MemberId Long memberId, @RequestBody CreateReservationMobileRequestV1 createReservationMobileRequestV1) {
        createReservationMobileRequestV1.setActionMemberId(memberId);
        createReservationMobileRequestV1.setMemberId(memberId);
        createReservationMobileRequestV1.setCenterId(createReservationMobileRequestV1.getCenterId());
        createReservationMobileRequestV1.setStatus(ReservationStatus.INAPP_RESERVATION);
        reservationMobileService.createReservation(createReservationMobileRequestV1);
        return ResponseEntity.ok(CreateReservationAdminResponseV1.builder().success(true).build());
    }

    @PostMapping("/v1/mobile/reservation/payment/confirm")
    public ResponseEntity<CreateReservationWithPaymentConfirmMobileResponseV1> createReservationWithPaymentConfirm(final @MemberId Long memberId,
                                                                            final @RequestBody CreateReservationWithPaymentConfirmMobileRequestV1 createReservationWithPaymentConfirmMobileRequestV1) {
        createReservationWithPaymentConfirmMobileRequestV1.setMemberId(memberId);
        reservationMobileService.createReservationWithPaymentConfirm(createReservationWithPaymentConfirmMobileRequestV1);
        return ResponseEntity.ok(CreateReservationWithPaymentConfirmMobileResponseV1.builder().success(true).build());
    }

    @PostMapping("/v1/mobile/reservation/validate")
    public ResponseEntity<Void> validateReservationBeforePayment(final @MemberId Long memberId, final @RequestBody ValidationReservationBeforePaymentMobileRequestV1 validationReservationBeforePaymentMobileRequestV1) {
        validationReservationBeforePaymentMobileRequestV1.setMemberId(memberId);
        reservationMobileService.validateReservationBeforePayment(validationReservationBeforePaymentMobileRequestV1);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/v1/mobile/reservation/cancel")
    public ResponseEntity<CancelReservationAdminResponseV1> cancelReservation(@MemberId Long memberId, @RequestBody CancelReservationMobileRequestV1 cancelReservationMobileRequestV1) {
        cancelReservationMobileRequestV1.setActionMemberId(memberId);
        cancelReservationMobileRequestV1.setStatus(ReservationStatus.MEMBER_CANCELED_RESERVATION);
        reservationMobileService.cancelReservation(cancelReservationMobileRequestV1);
        return ResponseEntity.ok(CancelReservationAdminResponseV1.builder().success(true).build());
    }

    @PostMapping("/v1/mobile/reservation/refund")
    public ResponseEntity<Void> refundReservation(@MemberId Long memberId,  @RequestBody RefundReservationMobileRequestV1 refundReservationMobileRequestV1) {
        reservationMobileService.refundReservation(memberId, refundReservationMobileRequestV1);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/mobile/reservation/list")
    public ResponseEntity<List<GetReservationMobileResponse>> getReservationList(@MemberId Long memberId) {
        final List<ReservationMobileDto> reservationMobileDtoList = reservationMobileService.getReservationList(memberId);
        final List<GetReservationMobileResponse> getReservationMobileResponseList = reservationMobileDtoList
                .stream()
                .map(reservationMobileDto -> GetReservationMobileResponse.builder()
                        .reservationId(reservationMobileDto.getReservationDto().getId())
                        .lectureId(reservationMobileDto.getWellnessLectureDto().getId())
                        .lectureImageUrl(reservationMobileDto.getWellnessLectureDto().getLectureImageUrlList().get(0))
                        .lectureTypeName(reservationMobileDto.getWellnessLectureTypeDto().name())
                        .lectureTypeDescription(reservationMobileDto.getWellnessLectureTypeDto().description())
                        .lectureName(reservationMobileDto.getWellnessLectureDto().getName())
                        .reservationStatus(reservationMobileDto.getReservationDto().getStatus())
                        .lectureStartDateTime(reservationMobileDto.getWellnessLectureDto().getStartDateTime())
                        .lectureEndDateTime(reservationMobileDto.getWellnessLectureDto().getEndDateTime())
                        .teacherName(reservationMobileDto.getTeacherDto().getName())
                        .lectureRoom(reservationMobileDto.getWellnessLectureDto().getRoom())
                        .centerName(reservationMobileDto.getCenterDto().getName())
                        .build())
                .toList();

        return ResponseEntity.ok(getReservationMobileResponseList);
    }

    @GetMapping("/v1/mobile/reservation/list/check-in")
    public ResponseEntity<List<GetReservationCheckInMobileResponse>> getReservationCheckInList(@MemberId Long memberId) {
        final List<ReservationMobileDto> reservationMobileDtoList = reservationMobileService.getReservationCheckInList(memberId);
        final List<GetReservationCheckInMobileResponse> getReservationMobileResponseList = reservationMobileDtoList
                .stream()
                .map(reservationMobileDto -> GetReservationCheckInMobileResponse.builder()
                        .reservationId(reservationMobileDto.getReservationDto().getId())
                        .lectureId(reservationMobileDto.getWellnessLectureDto().getId())
                        .lectureImageUrl(reservationMobileDto.getWellnessLectureDto().getLectureImageUrlList().get(0))
                        .lectureTypeName(reservationMobileDto.getWellnessLectureTypeDto().name())
                        .lectureTypeDescription(reservationMobileDto.getWellnessLectureTypeDto().description())
                        .lectureName(reservationMobileDto.getWellnessLectureDto().getName())
                        .reservationStatus(reservationMobileDto.getReservationDto().getStatus())
                        .lectureStartDateTime(reservationMobileDto.getWellnessLectureDto().getStartDateTime())
                        .lectureEndDateTime(reservationMobileDto.getWellnessLectureDto().getEndDateTime())
                        .teacherName(reservationMobileDto.getTeacherDto().getName())
                        .lectureRoom(reservationMobileDto.getWellnessLectureDto().getRoom())
                        .centerName(reservationMobileDto.getCenterDto().getName())
                        .lectureReviewId(reservationMobileDto.getMyWellnessLectureReviewDto() != null ? reservationMobileDto.getMyWellnessLectureReviewDto().getId() : null)
                        .rating(WellnessLectureReviewUtils.calculateAverageRating(reservationMobileDto.getWellnessLectureReviewDtoList()))
                        .reviewCnt(reservationMobileDto.getWellnessLectureReviewDtoList() != null ? reservationMobileDto.getWellnessLectureReviewDtoList().size() : null)
                        .build())
                .toList();

        return ResponseEntity.ok(getReservationMobileResponseList);
    }

}
