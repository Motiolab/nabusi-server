package com.positivehotel.nabusi_server.memberPackage.memberPoint.application.dto.request;

import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.domain.PointTransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateMemberPointMobileRequestV1 {
    private Long memberId;
    private Long amount;
    private PointTransactionType transactionType;
    private String description;
    private String referenceId;
}
