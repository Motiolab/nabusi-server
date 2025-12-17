package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class UpdateWellnessTicketIssuanceAdminRequestV1 {
    private Long id;
    private String name;
    private String backgroundColor;
    private String textColor;
    private String type;
    private ZonedDateTime startDate;
    private ZonedDateTime expireDate;
    private Integer remainingCnt;
    private String limitType;
    private Integer limitCnt;
    private Boolean isDelete;
    private Long actionMemberId;
}
