package com.motiolab.nabusi_server.reservation.domain;

import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="reservation")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long centerId;
    private Long memberId;
    private Long paymentId;
    private Long actionMemberId;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private Long wellnessLectureId;
    private Long wellnessTicketIssuanceId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static ReservationEntity create(final Long centerId, final Long memberId, final Long paymentId, final Long actionMemberId, final ReservationStatus status, final Long wellnessLectureId, final Long wellnessTicketIssuanceId) {
        return ReservationEntity.builder()
                .centerId(centerId)
                .memberId(memberId)
                .paymentId(paymentId)
                .actionMemberId(actionMemberId)
                .status(status)
                .wellnessLectureId(wellnessLectureId)
                .wellnessTicketIssuanceId(wellnessTicketIssuanceId)
                .build();
    }

    public void updateStatus(ReservationStatus status) {
        this.status = status;
    }
}
