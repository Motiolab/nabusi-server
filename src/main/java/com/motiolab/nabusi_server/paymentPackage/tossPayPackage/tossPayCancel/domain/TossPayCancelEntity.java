package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "toss_pay_cancel")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class TossPayCancelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static TossPayCancelEntity create(Long tossPayId, String transactionKey, String cancelReason, Integer taxExemptionAmount, String canceledAt, Integer transferDiscountAmount, Integer easyPayDiscountAmount, String receiptKey, Integer cancelAmount, Integer taxFreeAmount, Integer refundableAmount, String cancelStatus, String cancelRequestId) {
        return TossPayCancelEntity.builder()
                .tossPayId(tossPayId)
                .transactionKey(transactionKey)
                .cancelReason(cancelReason)
                .taxExemptionAmount(taxExemptionAmount)
                .canceledAt(canceledAt)
                .transferDiscountAmount(transferDiscountAmount)
                .easyPayDiscountAmount(easyPayDiscountAmount)
                .receiptKey(receiptKey)
                .cancelAmount(cancelAmount)
                .taxFreeAmount(taxFreeAmount)
                .refundableAmount(refundableAmount)
                .cancelStatus(cancelStatus)
                .cancelRequestId(cancelRequestId)
                .build();
    }
}
