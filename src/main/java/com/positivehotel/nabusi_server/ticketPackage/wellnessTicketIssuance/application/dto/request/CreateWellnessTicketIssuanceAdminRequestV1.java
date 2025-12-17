package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.request;

import com.positivehotel.nabusi_server.ticketPackage.enums.PaymentMethodEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class CreateWellnessTicketIssuanceAdminRequestV1 {
    private Long centerId;
    private ZonedDateTime startDate;
    private ZonedDateTime expireDate;
    private String limitType;
    private Integer limitCnt;
    private Integer totalUsableCnt;
    private Long memberId;
    private Long wellnessTicketId;
    private PaymentMethodEnum paymentMethod;
    private Integer discountRate;
    private Integer totalPayValue;
    private Integer unpaidValue;
    private Integer cardInstallment;
    private Integer cardPayValue;
    private Integer cashPayValue;
    private Long payerMemberId;
    private String note;
    private Long actionMemberId;
}
