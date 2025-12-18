package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.dto;

import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.domain.TossPayEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TossPayDto {
    private Long id;
    private Long memberId;
    private Long wellnessLectureId;
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private Integer taxExemptionAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private Boolean useEscrow;
    private Boolean cultureExpense;
    private Long tossPayCardId;
    private String type;
    private Long tossPayEasyPayId;
    private String country;
    private Long tossPayFailureId;
    private Boolean isPartialCancelable;
    private String receiptUrl;
    private String checkoutUrl;
    private String currency;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private Integer vat;
    private Integer taxFreeAmount;
    private String method;
    private String version;

    public static TossPayDto from(TossPayEntity tossPayEntity) {
        return TossPayDto.builder()
                .id(tossPayEntity.getId())
                .memberId(tossPayEntity.getMemberId())
                .wellnessLectureId(tossPayEntity.getWellnessLectureId())
                .mId(tossPayEntity.getMId())
                .lastTransactionKey(tossPayEntity.getLastTransactionKey())
                .paymentKey(tossPayEntity.getPaymentKey())
                .orderId(tossPayEntity.getOrderId())
                .orderName(tossPayEntity.getOrderName())
                .taxExemptionAmount(tossPayEntity.getTaxExemptionAmount())
                .status(tossPayEntity.getStatus())
                .requestedAt(tossPayEntity.getRequestedAt())
                .approvedAt(tossPayEntity.getApprovedAt())
                .useEscrow(tossPayEntity.getUseEscrow())
                .cultureExpense(tossPayEntity.getCultureExpense())
                .tossPayCardId(tossPayEntity.getTossPayCardId())
                .type(tossPayEntity.getType())
                .tossPayEasyPayId(tossPayEntity.getTossPayEasyPayId())
                .country(tossPayEntity.getCountry())
                .tossPayFailureId(tossPayEntity.getTossPayFailureId())
                .isPartialCancelable(tossPayEntity.getIsPartialCancelable())
                .receiptUrl(tossPayEntity.getReceiptUrl())
                .checkoutUrl(tossPayEntity.getCheckoutUrl())
                .currency(tossPayEntity.getCurrency())
                .totalAmount(tossPayEntity.getTotalAmount())
                .balanceAmount(tossPayEntity.getBalanceAmount())
                .suppliedAmount(tossPayEntity.getSuppliedAmount())
                .vat(tossPayEntity.getVat())
                .taxFreeAmount(tossPayEntity.getTaxFreeAmount())
                .method(tossPayEntity.getMethod())
                .version(tossPayEntity.getVersion())
                .build();
    }
}
