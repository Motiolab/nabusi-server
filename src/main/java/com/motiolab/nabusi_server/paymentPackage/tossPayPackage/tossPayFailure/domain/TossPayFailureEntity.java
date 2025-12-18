package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "toss_pay_failure")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class TossPayFailureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String message;

    public static TossPayFailureEntity create(String code, String message) {
        return TossPayFailureEntity.builder()
                .code(code)
                .message(message)
                .build();
    }
}
