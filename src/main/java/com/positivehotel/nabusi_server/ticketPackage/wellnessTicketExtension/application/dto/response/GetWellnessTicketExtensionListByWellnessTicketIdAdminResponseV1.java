package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1 {
    private String registerName;
    private ZonedDateTime targetDate;
    private Integer extendDate;
    private String reason;
    private ZonedDateTime createDateTime;
}
