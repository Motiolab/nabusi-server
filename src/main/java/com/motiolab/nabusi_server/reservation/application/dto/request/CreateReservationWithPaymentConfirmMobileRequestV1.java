package com.motiolab.nabusi_server.reservation.application.dto.request;

import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateReservationWithPaymentConfirmMobileRequestV1 {
    private String paymentKey;
    private String amount;
    private String orderId;
    private Long memberId;
    private Long wellnessLectureId;
    private Long centerId;
}
