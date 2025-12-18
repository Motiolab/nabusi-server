package com.motiolab.nabusi_server.reservation.application.dto.request;

import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CancelReservationMobileRequestV1 {
    private Long reservationId;
    private Long actionMemberId;
    private ReservationStatus status;
}
