package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.application.dto;

import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.domain.TossPayCardEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TossPayCardDto {
    private Long id;
    private String issuerCode;
    private String acquirerCode;
    private String number;
    private Integer installmentPlanMonths;
    private Boolean isInterestFree;
    private String interestPayer;
    private String approveNo;
    private Boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String acquireStatus;
    private String receiptUrl;
    private Integer amount;

    public static TossPayCardDto from(TossPayCardEntity tossPayCardEntity) {
        return TossPayCardDto.builder()
                .id(tossPayCardEntity.getId())
                .issuerCode(tossPayCardEntity.getIssuerCode())
                .acquirerCode(tossPayCardEntity.getAcquirerCode())
                .number(tossPayCardEntity.getNumber())
                .installmentPlanMonths(tossPayCardEntity.getInstallmentPlanMonths())
                .isInterestFree(tossPayCardEntity.getIsInterestFree())
                .interestPayer(tossPayCardEntity.getInterestPayer())
                .approveNo(tossPayCardEntity.getApproveNo())
                .useCardPoint(tossPayCardEntity.getUseCardPoint())
                .cardType(tossPayCardEntity.getCardType())
                .ownerType(tossPayCardEntity.getOwnerType())
                .acquireStatus(tossPayCardEntity.getAcquireStatus())
                .receiptUrl(tossPayCardEntity.getReceiptUrl())
                .amount(tossPayCardEntity.getAmount())
                .build();
    }
}
