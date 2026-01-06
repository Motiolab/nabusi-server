package com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberPointHistoryMobileResponse {
    private Long id;
    private String transactionType;
    private Long amount;
    private String description;
    private String referenceId;
    private String createdDate;
}
