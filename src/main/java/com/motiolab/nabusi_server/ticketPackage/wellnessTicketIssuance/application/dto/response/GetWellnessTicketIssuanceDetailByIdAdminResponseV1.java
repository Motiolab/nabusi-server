package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetWellnessTicketIssuanceDetailByIdAdminResponseV1 {
    private Long id;
    private Long memberId;
    private String memberName;
    private String mobile;
    private String ticketName;
    private ZonedDateTime startDate;
    private ZonedDateTime expireDate;
    private String type;
    private String backgroundColor;
    private Integer totalUsableCnt;
    private Integer remainingCnt;
    private String limitType;
    private Integer limitCnt;
    private Integer unpaidValue;
    private Boolean isDelete;
    private Long wellnessTicketId;
}
