package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class CreateWellnessTicketExtensionAdminRequestV1 {
    private Integer extendDate;
    private Boolean isApplyAfter;
    private Long registerId;
    private String reason;
    private ZonedDateTime targetDate;
    private Long wellnessTicketId;
}
