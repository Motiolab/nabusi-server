package com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application;

import com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.WellnessTicketExtensionDto;

import java.util.List;

public interface WellnessTicketExtensionService {
    void create(WellnessTicketExtensionDto wellnessTicketExtensionDto);
    List<WellnessTicketExtensionDto> getAllByWellnessTicketId(Long wellnessTicketId);
}
