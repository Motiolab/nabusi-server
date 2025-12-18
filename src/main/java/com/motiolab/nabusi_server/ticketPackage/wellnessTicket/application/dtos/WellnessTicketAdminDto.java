package com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;

import java.util.List;

public record WellnessTicketAdminDto(WellnessTicketDto wellnessTicketDto, List<WellnessClassDto> wellnessClassDtoList) {
}
