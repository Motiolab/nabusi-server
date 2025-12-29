package com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.response;

import lombok.*;

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
