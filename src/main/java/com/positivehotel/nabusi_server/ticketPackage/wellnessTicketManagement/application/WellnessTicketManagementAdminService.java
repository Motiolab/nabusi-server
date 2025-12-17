package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementAdminDto;

import java.util.List;

public interface WellnessTicketManagementAdminService {
    List<WellnessTicketManagementAdminDto> getWellnessTicketManagementNameListByCenterId(Long centerId);
}
