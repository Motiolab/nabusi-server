package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.domain.WellnessTicketExtensionEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
public class WellnessTicketExtensionDto {
    private Long id;
    private Integer extendDate;
    private Boolean isApplyAfter;
    private Long registerId;
    private String reason;
    private ZonedDateTime targetDate;
    private Long wellnessTicketId;
    private ZonedDateTime createDateTime;

    public static WellnessTicketExtensionDto from(WellnessTicketExtensionEntity wellnessTicketExtensionEntity) {
        return WellnessTicketExtensionDto.builder()
                .id(wellnessTicketExtensionEntity.getId())
                .extendDate(wellnessTicketExtensionEntity.getExtendDate())
                .isApplyAfter(wellnessTicketExtensionEntity.getIsApplyAfter())
                .registerId(wellnessTicketExtensionEntity.getRegisterId())
                .reason(wellnessTicketExtensionEntity.getReason())
                .targetDate(wellnessTicketExtensionEntity.getTargetDate())
                .wellnessTicketId(wellnessTicketExtensionEntity.getWellnessTicketId())
                .createDateTime(wellnessTicketExtensionEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
