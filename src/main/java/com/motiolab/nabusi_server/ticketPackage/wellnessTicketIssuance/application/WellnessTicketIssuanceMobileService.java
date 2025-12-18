package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application;

import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceMobileDto;

import java.util.List;

public interface WellnessTicketIssuanceMobileService {
    List<WellnessTicketIssuanceMobileDto> getMyWellnessTicketIssuanceList(Long memberId);
}
