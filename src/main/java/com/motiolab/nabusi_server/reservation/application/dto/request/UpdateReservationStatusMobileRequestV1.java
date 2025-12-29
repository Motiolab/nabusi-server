package com.motiolab.nabusi_server.reservation.application.dto.request;

import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateReservationStatusMobileRequestV1 {
    private Long reservationId;
    private ReservationStatus status;

    @Builder
    public UpdateReservationStatusMobileRequestV1(Long reservationId, ReservationStatus status) {
        this.reservationId = reservationId;
        this.status = status;
    }
}
