package com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.application.dto;

import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.domain.TossPayCancelEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TossPayCancelDto {
    private Long id;
    private Long tossPayId;
    private String transactionKey;
    private String cancelReason;
    private Integer taxExemptionAmount;
    private String canceledAt;
    private Integer transferDiscountAmount;
    private Integer easyPayDiscountAmount;
    private String receiptKey;
    private Integer cancelAmount;
    private Integer taxFreeAmount;
    private Integer refundableAmount;
    private String cancelStatus;
    private String cancelRequestId;

    public static TossPayCancelDto from(TossPayCancelEntity tossPayCancelEntity) {
        return TossPayCancelDto.builder()
                .id(tossPayCancelEntity.getId())
                .tossPayId(tossPayCancelEntity.getTossPayId())
                .transactionKey(tossPayCancelEntity.getTransactionKey())
                .cancelReason(tossPayCancelEntity.getCancelReason())
                .taxExemptionAmount(tossPayCancelEntity.getTaxExemptionAmount())
                .canceledAt(tossPayCancelEntity.getCanceledAt())
                .transferDiscountAmount(tossPayCancelEntity.getTransferDiscountAmount())
                .easyPayDiscountAmount(tossPayCancelEntity.getEasyPayDiscountAmount())
                .receiptKey(tossPayCancelEntity.getReceiptKey())
                .cancelAmount(tossPayCancelEntity.getCancelAmount())
                .taxFreeAmount(tossPayCancelEntity.getTaxFreeAmount())
                .refundableAmount(tossPayCancelEntity.getRefundableAmount())
                .cancelStatus(tossPayCancelEntity.getCancelStatus())
                .cancelRequestId(tossPayCancelEntity.getCancelRequestId())
                .build();

    }
}
