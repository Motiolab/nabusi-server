package com.positivehotel.nabusi_server.reservation.application.dto;

import com.positivehotel.nabusi_server.reservation.domain.ReservationEntity;
import com.positivehotel.nabusi_server.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class ReservationDto {
    private Long id;
    private Long centerId;
    private Long memberId;
    private Long paymentId;
    private Long actionMemberId;
    private ReservationStatus status;
    private Long wellnessLectureId;
    private Long wellnessTicketIssuanceId;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static ReservationDto from(final ReservationEntity reservationEntity) {
        return ReservationDto.builder()
                .id(reservationEntity.getId())
                .centerId(reservationEntity.getCenterId())
                .memberId(reservationEntity.getMemberId())
                .paymentId(reservationEntity.getPaymentId())
                .actionMemberId(reservationEntity.getActionMemberId())
                .status(reservationEntity.getStatus())
                .wellnessLectureId(reservationEntity.getWellnessLectureId())
                .wellnessTicketIssuanceId(reservationEntity.getWellnessTicketIssuanceId())
                .lastUpdatedDate(reservationEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(reservationEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
