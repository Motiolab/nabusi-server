package com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application;

import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementAdminDto;

import java.util.List;

public interface WellnessTicketManagementAdminService {
    List<WellnessTicketManagementAdminDto> getWellnessTicketManagementNameListByCenterId(Long centerId);
}
