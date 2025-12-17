package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.application.dto.WellnessTicketIssuanceHistoryDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.domain.WellnessTicketIssuanceHistoryEntity;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuanceHistory.domain.WellnessTicketIssuanceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WellnessTicketIssuanceHistoryServiceImpl implements WellnessTicketIssuanceHistoryService{
    private final WellnessTicketIssuanceHistoryRepository wellnessTicketIssuanceHistoryRepository;

    @Override
    public void create(WellnessTicketIssuanceHistoryDto wellnessTicketIssuanceHistoryDto) {
        final WellnessTicketIssuanceHistoryEntity wellnessTicketIssuanceHistoryEntity = WellnessTicketIssuanceHistoryEntity.create(
                wellnessTicketIssuanceHistoryDto.getChangedCnt(),
                wellnessTicketIssuanceHistoryDto.getWellnessLectureId(),
                wellnessTicketIssuanceHistoryDto.getReservationId(),
                wellnessTicketIssuanceHistoryDto.getActionMemberId(),
                wellnessTicketIssuanceHistoryDto.getWellnessTicketIssuanceId(),
                wellnessTicketIssuanceHistoryDto.getReason()
        );

        wellnessTicketIssuanceHistoryRepository.save(wellnessTicketIssuanceHistoryEntity);
    }
}
