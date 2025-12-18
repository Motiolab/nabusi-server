package com.motiolab.nabusi_server.policy.wellnessClass.application.dto;

import com.motiolab.nabusi_server.policy.wellnessClass.domain.PolicyWellnessClassEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PolicyWellnessClassDto {
    private Long id;
    private Long centerId;
    private Integer reservationStart;
    private Integer reservationEnd;
    private Integer reservationCancelLimit;
    private Integer autoReserveBeforeClassTime;
    private Integer autoAbsentLimit;
    private Boolean isActiveAutoReservation;

    public static PolicyWellnessClassDto from(PolicyWellnessClassEntity policyWellnessClassEntity) {
        return PolicyWellnessClassDto.builder()
                .id(policyWellnessClassEntity.getId())
                .centerId(policyWellnessClassEntity.getCenterId())
                .reservationStart(policyWellnessClassEntity.getReservationStart())
                .reservationEnd(policyWellnessClassEntity.getReservationEnd())
                .reservationCancelLimit(policyWellnessClassEntity.getReservationCancelLimit())
                .autoReserveBeforeClassTime(policyWellnessClassEntity.getAutoReserveBeforeClassTime())
                .autoAbsentLimit(policyWellnessClassEntity.getAutoAbsentLimit())
                .isActiveAutoReservation(policyWellnessClassEntity.getIsActiveAutoReservation())
                .build();
    }
}
