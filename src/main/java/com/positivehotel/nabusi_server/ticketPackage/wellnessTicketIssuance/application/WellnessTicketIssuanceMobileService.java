package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceMobileDto;

import java.util.List;

public interface WellnessTicketIssuanceMobileService {
    List<WellnessTicketIssuanceMobileDto> getMyWellnessTicketIssuanceList(Long memberId);
}
