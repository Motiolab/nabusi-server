package com.motiolab.nabusi_server.paymentPackage.onSitePay.domain;

import com.motiolab.nabusi_server.ticketPackage.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Table(name = "on_site_pay")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class OnSitePayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long paymentId;
    private Integer discountRate;
    private Integer totalPayValue;
    private Integer unpaidValue;
    private Integer cardInstallment;
    private Integer cardPayValue;
    private Integer cashPayValue;
    private Long payerMemberId;
    private Long payeeMemberId;
    private String note;
    private ZonedDateTime paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;
    private Long updateMemberId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static OnSitePayEntity create(Long paymentId, Integer discountRate, Integer totalPayValue, Integer unpaidValue, Integer cardInstallment, Integer cardPayValue, Integer cashPayValue, Long payerMemberId, Long payeeMemberId, String note, ZonedDateTime paymentDate, PaymentMethodEnum paymentMethod, Long updateMemberId) {
        return OnSitePayEntity.builder()
                .paymentId(paymentId)
                .discountRate(discountRate)
                .totalPayValue(totalPayValue)
                .unpaidValue(unpaidValue)
                .cardInstallment(cardInstallment)
                .cardPayValue(cardPayValue)
                .cashPayValue(cashPayValue)
                .payerMemberId(payerMemberId)
                .payeeMemberId(payeeMemberId)
                .note(note)
                .paymentDate(paymentDate)
                .paymentMethod(paymentMethod)
                .updateMemberId(updateMemberId)
                .build();
    }
}
