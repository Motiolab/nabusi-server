package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "toss_pay_card")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class TossPayCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static TossPayCardEntity create(String issuerCode, String acquirerCode, String number, Integer installmentPlanMonths, Boolean isInterestFree, String interestPayer, String approveNo, Boolean useCardPoint, String cardType, String ownerType, String acquireStatus, String receiptUrl, Integer amount) {
        return TossPayCardEntity.builder()
                .issuerCode(issuerCode)
                .acquirerCode(acquirerCode)
                .number(number)
                .installmentPlanMonths(installmentPlanMonths)
                .isInterestFree(isInterestFree)
                .interestPayer(interestPayer)
                .approveNo(approveNo)
                .useCardPoint(useCardPoint)
                .cardType(cardType)
                .ownerType(ownerType)
                .acquireStatus(acquireStatus)
                .receiptUrl(receiptUrl)
                .amount(amount)
                .build();
    }
}
