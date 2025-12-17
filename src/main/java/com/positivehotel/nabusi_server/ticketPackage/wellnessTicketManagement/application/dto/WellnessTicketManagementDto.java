package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.domain.WellnessTicketManagementEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class WellnessTicketManagementDto {
    private Long id;
    private Long centerId;
    private Long wellnessTicketId;
    private String wellnessTicketIssuanceName;
    private List<Long> wellnessTicketIssuanceIdList;

    public static WellnessTicketManagementDto from(WellnessTicketManagementEntity wellnessTicketManagementEntity) {
        return WellnessTicketManagementDto.builder()
                .id(wellnessTicketManagementEntity.getId())
                .centerId(wellnessTicketManagementEntity.getCenterId())
                .wellnessTicketId(wellnessTicketManagementEntity.getWellnessTicketId())
                .wellnessTicketIssuanceName(wellnessTicketManagementEntity.getWellnessTicketIssuanceName())
                .wellnessTicketIssuanceIdList(wellnessTicketManagementEntity.getWellnessTicketIssuanceIdList())
                .build();
    }
}
