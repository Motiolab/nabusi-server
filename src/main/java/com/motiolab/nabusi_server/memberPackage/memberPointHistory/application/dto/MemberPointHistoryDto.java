package com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto;

import com.motiolab.nabusi_server.memberPackage.memberPointHistory.domain.MemberPointHistoryEntity;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.domain.PointTransactionType;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
public class MemberPointHistoryDto {
    private Long id;
    private Long memberId;
    private PointTransactionType transactionType;
    private Long amount;
    private String description;
    private String referenceId;
    private ZonedDateTime createdDate;

    public static MemberPointHistoryDto from(MemberPointHistoryEntity memberPointHistoryEntity) {
        return MemberPointHistoryDto.builder()
                .id(memberPointHistoryEntity.getId())
                .memberId(memberPointHistoryEntity.getMemberId())
                .transactionType(memberPointHistoryEntity.getTransactionType())
                .amount(memberPointHistoryEntity.getAmount())
                .description(memberPointHistoryEntity.getDescription())
                .referenceId(memberPointHistoryEntity.getReferenceId())
                .createdDate(memberPointHistoryEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
