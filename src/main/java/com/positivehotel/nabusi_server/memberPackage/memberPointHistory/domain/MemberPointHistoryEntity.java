package com.positivehotel.nabusi_server.memberPackage.memberPointHistory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member_point_history")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MemberPointHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private PointTransactionType transactionType;
    private Long amount;
    private String description;
    private String referenceId;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public static MemberPointHistoryEntity create(Long memberId, PointTransactionType transactionType, Long amount,
            String description, String referenceId) {
        return MemberPointHistoryEntity.builder()
                .memberId(memberId)
                .transactionType(transactionType)
                .amount(amount)
                .description(description)
                .referenceId(referenceId)
                .build();
    }
}
