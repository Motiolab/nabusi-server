package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "toss_pay")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class TossPayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static TossPayEntity create(Long memberId, Long wellnessLectureId, String mId, String lastTransactionKey, String paymentKey, String orderId, String orderName, Integer taxExemptionAmount, String status, String requestedAt, String approvedAt, Boolean useEscrow, Boolean cultureExpense, Long tossPayCardId, String type, Long tossPayEasyPayId, String country, Long tossPayFailureId, Boolean isPartialCancelable, String receiptUrl, String checkoutUrl, String currency, Integer totalAmount, Integer balanceAmount, Integer suppliedAmount, Integer vat, Integer taxFreeAmount, String method, String version) {
        return TossPayEntity.builder()
                .memberId(memberId)
                .wellnessLectureId(wellnessLectureId)
                .mId(mId)
                .lastTransactionKey(lastTransactionKey)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .orderName(orderName)
                .taxExemptionAmount(taxExemptionAmount)
                .status(status)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .useEscrow(useEscrow)
                .cultureExpense(cultureExpense)
                .tossPayCardId(tossPayCardId)
                .type(type)
                .tossPayEasyPayId(tossPayEasyPayId)
                .country(country)
                .tossPayFailureId(tossPayFailureId)
                .isPartialCancelable(isPartialCancelable)
                .receiptUrl(receiptUrl)
                .checkoutUrl(checkoutUrl)
                .currency(currency)
                .totalAmount(totalAmount)
                .balanceAmount(balanceAmount)
                .suppliedAmount(suppliedAmount)
                .vat(vat)
                .taxFreeAmount(taxFreeAmount)
                .method(method)
                .version(version)
                .build();
    }

    public void update(Long memberId, Long wellnessLectureId, String mId, String lastTransactionKey, String paymentKey, String orderId, String orderName, Integer taxExemptionAmount, String status, String requestedAt, String approvedAt, Boolean useEscrow, Boolean cultureExpense, Long tossPayCardId, String type, Long tossPayEasyPayId, String country, Long tossPayFailureId, Boolean isPartialCancelable, String receiptUrl, String checkoutUrl, String currency, Integer totalAmount, Integer balanceAmount, Integer suppliedAmount, Integer vat, Integer taxFreeAmount, String method, String version) {
        if(memberId != null) setMemberId(memberId);
        if(wellnessLectureId != null) setWellnessLectureId(wellnessLectureId);
        if(mId != null) setMId(mId);
        if(lastTransactionKey != null) setLastTransactionKey(lastTransactionKey);
        if(paymentKey != null) setPaymentKey(paymentKey);
        if(orderId != null) setOrderId(orderId);
        if(orderName != null) setOrderName(orderName);
        if(taxExemptionAmount != null) setTaxExemptionAmount(taxExemptionAmount);
        if(status != null) setStatus(status);
        if(requestedAt != null) setRequestedAt(requestedAt);
        if(approvedAt != null) setApprovedAt(approvedAt);
        if(useEscrow != null) setUseEscrow(useEscrow);
        if(cultureExpense != null) setCultureExpense(cultureExpense);
        if(tossPayCardId != null) setTossPayCardId(tossPayCardId);
        if(type != null) setType(type);
        if(tossPayEasyPayId != null) setTossPayEasyPayId(tossPayEasyPayId);
        if(country != null) setCountry(country);
        if(tossPayFailureId != null) setTossPayFailureId(tossPayFailureId);
        if(isPartialCancelable != null) setIsPartialCancelable(isPartialCancelable);
        if(receiptUrl != null) setReceiptUrl(receiptUrl);
        if(checkoutUrl != null) setCheckoutUrl(checkoutUrl);
        if(currency != null) setCurrency(currency);
        if(totalAmount != null) setTotalAmount(totalAmount);
        if(balanceAmount != null) setBalanceAmount(balanceAmount);
        if(suppliedAmount != null) setSuppliedAmount(suppliedAmount);
        if(vat != null) setVat(vat);
        if(taxFreeAmount != null) setTaxFreeAmount(taxFreeAmount);
        if(method != null) setMethod(method);
        if(version != null) setVersion(version);
    }
}
