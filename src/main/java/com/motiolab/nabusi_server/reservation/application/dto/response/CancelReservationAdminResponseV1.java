package com.motiolab.nabusi_server.reservation.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CancelReservationAdminResponseV1 {
    private Boolean success;
}
