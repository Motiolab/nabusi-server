package com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application;

import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;

import java.util.List;

public interface WellnessTicketManagementService {
    WellnessTicketManagementDto create(WellnessTicketManagementDto wellnessTicketManagementDto);
    List<WellnessTicketManagementDto> getAllByWellnessTicketId(Long wellnessTicketId);
    void deleteByIdList(List<Long> idList);
    WellnessTicketManagementDto getByWellnessTicketIdAndWellnessTicketIssuanceName(Long wellnessTicketId, String wellnessTicketIssuanceName);
    WellnessTicketManagementDto update(WellnessTicketManagementDto wellnessTicketManagementDto);
    List<WellnessTicketManagementDto> getAllByIdList(List<Long> idList);
    List<WellnessTicketManagementDto> getAllByCenterId(Long centerId);
    WellnessTicketManagementDto getByCenterIdAndWellnessTicketIdAndWellnessTicketIssuanceName(Long centerId, Long wellnessTicketId, String wellnessTicketIssuanceName);
    WellnessTicketManagementDto getByWellnessTicketIssuanceId(Long wellnessTicketIssuanceId);
}
