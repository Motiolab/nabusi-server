package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WellnessTicketManagementAdminDto {
    private WellnessTicketManagementDto wellnessTicketManagementDto;
    private WellnessTicketDto wellnessTicketDto;
}
