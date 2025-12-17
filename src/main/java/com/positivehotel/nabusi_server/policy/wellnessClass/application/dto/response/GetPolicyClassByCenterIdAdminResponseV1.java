package com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPolicyClassByCenterIdAdminResponseV1 {
    private Long id;
    private Long centerId;
    private Integer reservationStart;
    private Integer reservationEnd;
    private Integer reservationCancelLimit;
    private Integer autoReserveBeforeClassTime;
    private Integer autoAbsentLimit;
    private Boolean isActiveAutoReservation;
}
