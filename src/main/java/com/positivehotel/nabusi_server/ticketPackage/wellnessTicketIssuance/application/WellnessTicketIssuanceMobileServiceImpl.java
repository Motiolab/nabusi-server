package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceMobileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessTicketIssuanceMobileServiceImpl implements WellnessTicketIssuanceMobileService{
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;

    @Override
    public List<WellnessTicketIssuanceMobileDto> getMyWellnessTicketIssuanceList(Long memberId) {
        final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByMemberId(memberId);
        return wellnessTicketIssuanceDtoList
                .stream()
                .filter(wellnessTicketIssuanceDto -> !wellnessTicketIssuanceDto.getIsDelete() && wellnessTicketIssuanceDto.getRemainingCnt() > 0 && wellnessTicketIssuanceDto.getExpireDate().isAfter(ZonedDateTime.now()))
                .map(wellnessTicketIssuanceDto -> WellnessTicketIssuanceMobileDto.builder()
                        .wellnessTicketIssuanceDto(wellnessTicketIssuanceDto)
                        .build())
                .toList();
    }
}
