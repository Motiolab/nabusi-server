package com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateOnSitePayUnpaidAdminRequestV1 {
    private Long paymentId;
    private Long wellnessTicketIssuanceId;
    private Integer totalPayValue;
    private Integer unpaidValue;
    private Integer cardInstallment;
    private Integer cardPayValue;
    private Integer cashPayValue;
    private Long payerMemberId;
    private Long payeeMemberId;
    private String note;
    private Long actionMemberId;
}
