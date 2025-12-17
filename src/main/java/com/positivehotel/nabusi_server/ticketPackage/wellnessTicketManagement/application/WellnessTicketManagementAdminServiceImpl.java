package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application;

import com.positivehotel.nabusi_server.exception.customException.NotFoundException;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.WellnessTicketService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementAdminDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessTicketManagementAdminServiceImpl implements WellnessTicketManagementAdminService{
    private final WellnessTicketManagementService wellnessTicketManagementService;
    private final WellnessTicketService wellnessTicketService;

    @Override
    public List<WellnessTicketManagementAdminDto> getWellnessTicketManagementNameListByCenterId(Long centerId) {
        final List<WellnessTicketManagementDto> wellnessTicketManagementDtoList = wellnessTicketManagementService.getAllByCenterId(centerId);
        final List<Long> wellnessTicketIdList = wellnessTicketManagementDtoList.stream().map(WellnessTicketManagementDto::getWellnessTicketId).distinct().toList();
        final List<WellnessTicketDto> wellnessTicketDtoList = wellnessTicketService.getAllById(wellnessTicketIdList);

        return wellnessTicketManagementDtoList.stream().map((wellnessTicketManagementDto) -> WellnessTicketManagementAdminDto.builder()
                        .wellnessTicketManagementDto(wellnessTicketManagementDto)
                        .wellnessTicketDto(wellnessTicketDtoList.stream().filter(wellnessTicketDto -> wellnessTicketDto.getId().equals(wellnessTicketManagementDto.getWellnessTicketId())).findFirst().orElseThrow(() -> new NotFoundException(WellnessTicketManagementAdminServiceImpl.class, wellnessTicketManagementDto.getWellnessTicketId())))
                        .build())
                .toList();
    }
}
