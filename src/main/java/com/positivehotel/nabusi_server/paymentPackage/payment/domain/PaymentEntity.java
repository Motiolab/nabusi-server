package com.positivehotel.nabusi_server.paymentPackage.payment.domain;

import com.positivehotel.nabusi_server.paymentPackage.payment.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "payment")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long tossPayId;
    private Long onSitePayId;
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static PaymentEntity create(Long memberId, Long tossPayId, Long onSitePayId, PaymentStatusEnum status) {
        return PaymentEntity.builder()
                .memberId(memberId)
                .tossPayId(tossPayId)
                .onSitePayId(onSitePayId)
                .status(status)
                .build();
    }

    public void update(Long memberId, Long tossPayId, Long onSitePayId, PaymentStatusEnum status) {
        if(memberId != null) setMemberId(memberId);
        if(tossPayId != null) setTossPayId(tossPayId);
        if(onSitePayId != null) setOnSitePayId(onSitePayId);
        if(status != null) setStatus(status);
    }
}
