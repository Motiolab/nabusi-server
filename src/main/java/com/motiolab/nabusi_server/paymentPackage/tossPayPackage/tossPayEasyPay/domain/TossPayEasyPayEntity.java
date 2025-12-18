package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "toss_pay_easy_pay")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class TossPayEasyPayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String provider;
    private Integer amount;
    private Integer discountAmount;

    public static TossPayEasyPayEntity create(String provider, Integer amount, Integer discountAmount) {
        return TossPayEasyPayEntity.builder()
                .provider(provider)
                .amount(amount)
                .discountAmount(discountAmount)
                .build();
    }
}
