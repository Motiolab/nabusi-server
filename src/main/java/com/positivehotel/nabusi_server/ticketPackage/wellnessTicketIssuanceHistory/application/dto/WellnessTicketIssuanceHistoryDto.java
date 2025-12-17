package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.dto;

import com.positivehotel.nabusi_server.ticketPackage.enums.CntChangedReason;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.domain.WellnessTicketIssuanceHistoryEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
public class WellnessTicketIssuanceHistoryDto {
    private Long id;
    private Integer changedCnt;
    private Long wellnessLectureId;
    private Long reservationId;
    private Long actionMemberId;
    private Long wellnessTicketIssuanceId;
    private CntChangedReason reason;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static WellnessTicketIssuanceHistoryDto from(WellnessTicketIssuanceHistoryEntity wellnessTicketIssuanceHistoryEntity) {
        return WellnessTicketIssuanceHistoryDto.builder()
                .id(wellnessTicketIssuanceHistoryEntity.getId())
                .changedCnt(wellnessTicketIssuanceHistoryEntity.getChangedCnt())
                .wellnessLectureId(wellnessTicketIssuanceHistoryEntity.getWellnessLectureId())
                .reservationId(wellnessTicketIssuanceHistoryEntity.getReservationId())
                .actionMemberId(wellnessTicketIssuanceHistoryEntity.getActionMemberId())
                .wellnessTicketIssuanceId(wellnessTicketIssuanceHistoryEntity.getWellnessTicketIssuanceId())
                .reason(wellnessTicketIssuanceHistoryEntity.getReason())
                .lastUpdatedDate(wellnessTicketIssuanceHistoryEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(wellnessTicketIssuanceHistoryEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
