package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetMyWellnessTicketIssuanceListMobileResponse {
    private Long id;
    private String wellnessTicketIssuanceName;
    private String type;
    private String backgroundColor;
    private Integer remainingCnt;
    private Long remainingDate;
    private Integer totalUsableCnt;
    private ZonedDateTime startDate;
    private ZonedDateTime expireDate;
    private Boolean isDelete;
    private Integer unpaidValue;
    private String limitType;
    private Integer limitCnt;
    private ZonedDateTime createdDateTime;
}
