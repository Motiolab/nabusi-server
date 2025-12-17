package com.positivehotel.nabusi_server.policy.wellnessClass.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdatePolicyClassByCenterIdAdminRequestV1 {
    private Long id;
    private Long centerId;
    private Integer reservationStart;
    private Integer reservationEnd;
    private Integer reservationCancelLimit;
    private Integer autoReserveBeforeClassTime;
    private Integer autoAbsentLimit;
    private Boolean isActiveAutoReservation;
}
