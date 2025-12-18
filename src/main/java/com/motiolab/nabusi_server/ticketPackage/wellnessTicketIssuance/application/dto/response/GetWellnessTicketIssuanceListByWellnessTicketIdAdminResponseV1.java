package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetWellnessTicketIssuanceListByWellnessTicketIdAdminResponseV1 {
    private Long id;
    private String memberName;
    private String wellnessTicketIssuanceName;
    private String mobile;
    private String type;
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
