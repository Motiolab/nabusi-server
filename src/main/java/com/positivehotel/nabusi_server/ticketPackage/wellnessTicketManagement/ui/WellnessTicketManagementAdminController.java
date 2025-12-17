package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.ui;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.WellnessTicketManagementAdminService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementAdminDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.response.GetWellnessTicketManagementNameListByCenterIdAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessTicketManagementAdminController {
    private final WellnessTicketManagementAdminService wellnessTicketManagementAdminService;

    @GetMapping("/v1/admin/wellness-ticket-management/name/{centerId}")
    public ResponseEntity<List<GetWellnessTicketManagementNameListByCenterIdAdminResponseV1>> getWellnessTicketManagementNameListByCenterId(@PathVariable Long centerId) {
        final List<WellnessTicketManagementAdminDto> wellnessTicketManagementAdminDtoList = wellnessTicketManagementAdminService.getWellnessTicketManagementNameListByCenterId(centerId);

        final List<GetWellnessTicketManagementNameListByCenterIdAdminResponseV1> wellnessTicketManagementNameListByCenterIdAdminResponseV1List = wellnessTicketManagementAdminDtoList
                .stream()
                .map(wellnessTicketManagementAdminDto -> GetWellnessTicketManagementNameListByCenterIdAdminResponseV1.builder()
                        .id(wellnessTicketManagementAdminDto.getWellnessTicketManagementDto().getId())
                        .wellnessTicketIssuanceName(wellnessTicketManagementAdminDto.getWellnessTicketManagementDto().getWellnessTicketIssuanceName())
                        .wellnessTicketId(wellnessTicketManagementAdminDto.getWellnessTicketManagementDto().getWellnessTicketId())
                        .wellnessTicketName(wellnessTicketManagementAdminDto.getWellnessTicketDto().getName())
                        .build()
                )
                .toList();

        return ResponseEntity.ok(wellnessTicketManagementNameListByCenterIdAdminResponseV1List);
    }


}
