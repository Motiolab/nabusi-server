package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.domain;

import com.motiolab.nabusi_server.ticketPackage.enums.CntChangedReason;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="wellness_ticket_issuance_history")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessTicketIssuanceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer changedCnt;
    private Long wellnessLectureId;
    private Long reservationId;
    private Long actionMemberId;
    private Long wellnessTicketIssuanceId;
    @Enumerated(EnumType.STRING)
    private CntChangedReason reason;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessTicketIssuanceHistoryEntity create(final Integer changedCnt, final Long wellnessLectureId, final Long reservationId, final Long actionMemberId, final Long wellnessTicketIssuanceId, final CntChangedReason reason) {
        return WellnessTicketIssuanceHistoryEntity.builder()
                .changedCnt(changedCnt)
                .wellnessLectureId(wellnessLectureId)
                .reservationId(reservationId)
                .actionMemberId(actionMemberId)
                .wellnessTicketIssuanceId(wellnessTicketIssuanceId)
                .reason(reason)
                .build();
    }
}
