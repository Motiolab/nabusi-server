package com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.WellnessTicketExtensionAdminService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.WellnessTicketExtensionAdminDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.request.CreateWellnessTicketExtensionAdminRequestV1;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.response.GetWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessTicketExtensionAdminController {
    private final WellnessTicketExtensionAdminService wellnessTicketExtensionAdminService;

    @PostMapping("/v1/admin/wellness-ticket-extension/{centerId}")
    public ResponseEntity<Boolean> createWellnessTicketExtension(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody CreateWellnessTicketExtensionAdminRequestV1 createWellnessTicketExtensionAdminRequestV1) {
        createWellnessTicketExtensionAdminRequestV1.setRegisterId(memberId);
        wellnessTicketExtensionAdminService.createWellnessTicketExtension(createWellnessTicketExtensionAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/wellness-ticket-extension/{centerId}")
    public ResponseEntity<List<GetWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1>> getWellnessTicketExtensionListByWellnessTicketId(@PathVariable Long centerId, @RequestParam(defaultValue = "wellnessTicketId") Long wellnessTicketId) {
        final List<WellnessTicketExtensionAdminDto> getWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1List = wellnessTicketExtensionAdminService.getWellnessTicketExtensionListByWellnessTicketId(wellnessTicketId);
        final List<GetWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1> wellnessTicketExtensionListByWellnessTicketIdAdminResponseV1List = getWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1List
                .stream()
                .map(wellnessTicketExtensionAdminDto -> GetWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1.builder()
                        .registerName(wellnessTicketExtensionAdminDto.getRegisterMemberDto().getName())
                        .targetDate(wellnessTicketExtensionAdminDto.getWellnessTicketExtensionDto().getTargetDate())
                        .extendDate(wellnessTicketExtensionAdminDto.getWellnessTicketExtensionDto().getExtendDate())
                        .reason(wellnessTicketExtensionAdminDto.getWellnessTicketExtensionDto().getReason())
                        .createDateTime(wellnessTicketExtensionAdminDto.getWellnessTicketExtensionDto().getCreateDateTime())
                        .build())
                .toList();

        final List<GetWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1> sortedList = wellnessTicketExtensionListByWellnessTicketIdAdminResponseV1List
                .stream()
                .sorted(Comparator.comparing(GetWellnessTicketExtensionListByWellnessTicketIdAdminResponseV1::getCreateDateTime).reversed())
                .toList();

        return ResponseEntity.ok(sortedList);
    }
}
