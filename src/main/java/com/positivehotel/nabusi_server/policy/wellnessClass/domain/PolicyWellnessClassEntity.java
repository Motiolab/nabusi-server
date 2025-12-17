package com.positivehotel.nabusi_server.policy.wellnessClass.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="policy_wellness_class")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class PolicyWellnessClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long centerId;
    private Integer reservationStart;
    private Integer reservationEnd;
    private Integer reservationCancelLimit;
    private Integer autoReserveBeforeClassTime;
    private Integer autoAbsentLimit;
    private Boolean isActiveAutoReservation;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static PolicyWellnessClassEntity create(Long centerId, Integer reservationStart, Integer reservationEnd, Integer reservationCancelLimit, Integer autoReserveBeforeClassTime, Integer autoAbsentLimit, Boolean isActiveAutoReservation) {
        return PolicyWellnessClassEntity.builder()
                .centerId(centerId)
                .reservationStart(reservationStart)
                .reservationEnd(reservationEnd)
                .reservationCancelLimit(reservationCancelLimit)
                .autoReserveBeforeClassTime(autoReserveBeforeClassTime)
                .autoAbsentLimit(autoAbsentLimit)
                .isActiveAutoReservation(isActiveAutoReservation)
                .build();
    }

    public void put(Long centerId, Integer reservationStart, Integer reservationEnd, Integer reservationCancelLimit, Integer autoReserveBeforeClassTime, Integer autoAbsentLimit, Boolean isActiveAutoReservation) {
        setCenterId(centerId);
        setReservationStart(reservationStart);
        setReservationEnd(reservationEnd);
        setReservationCancelLimit(reservationCancelLimit);
        setAutoReserveBeforeClassTime(autoReserveBeforeClassTime);
        setAutoAbsentLimit(autoAbsentLimit);
        setIsActiveAutoReservation(isActiveAutoReservation);
    }

    public void patch(Long centerId, Integer reservationStart, Integer reservationEnd, Integer reservationCancelLimit, Integer autoReserveBeforeClassTime, Integer autoAbsentLimit, Boolean isActiveAutoReservation) {
        if(centerId != null) setCenterId(centerId);
        if(reservationStart != null) setReservationStart(reservationStart);
        if(reservationEnd != null) setReservationEnd(reservationEnd);
        if(reservationCancelLimit != null) setReservationCancelLimit(reservationCancelLimit);
        if(autoReserveBeforeClassTime != null) setAutoReserveBeforeClassTime(autoReserveBeforeClassTime);
        if(autoAbsentLimit != null) setAutoAbsentLimit(autoAbsentLimit);
        if(isActiveAutoReservation != null) setIsActiveAutoReservation(isActiveAutoReservation);
    }
}
