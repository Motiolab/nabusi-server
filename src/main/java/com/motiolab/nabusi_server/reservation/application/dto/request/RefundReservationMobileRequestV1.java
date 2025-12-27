package com.motiolab.nabusi_server.reservation.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RefundReservationMobileRequestV1 {
    private Long reservationId;
    private String cancelReason;
}
