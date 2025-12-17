package com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.ui;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.WellnessTicketAdminService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketAdminDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.request.CreateWellnessTicketAdminRequestV1;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.request.UpdateWellnessTicketAdminRequestV1;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.response.GetWellnessTicketAdminResponseV1;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.response.GetWellnessTicketDetailAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessTicketAdminController {
    private final WellnessTicketAdminService wellnessTicketAdminService;

    @GetMapping("/v1/admin/wellness-ticket/{centerId}")
    public ResponseEntity<List<GetWellnessTicketAdminResponseV1>> getWellnessTicketList(@PathVariable Long centerId) {
        final List<GetWellnessTicketAdminResponseV1> wellnessTicketAdminResponses = wellnessTicketAdminService.getAllWellnessTicketByCenterId(centerId)
                .stream()
                .map(wellnessTicketAdminDto -> GetWellnessTicketAdminResponseV1.builder()
                        .id(wellnessTicketAdminDto.wellnessTicketDto().getId())
                        .type(wellnessTicketAdminDto.wellnessTicketDto().getType())
                        .name(wellnessTicketAdminDto.wellnessTicketDto().getName())
                        .backgroundColor(wellnessTicketAdminDto.wellnessTicketDto().getBackgroundColor())
                        .textColor(wellnessTicketAdminDto.wellnessTicketDto().getTextColor())
                        .limitType(wellnessTicketAdminDto.wellnessTicketDto().getLimitType())
                        .limitCnt(wellnessTicketAdminDto.wellnessTicketDto().getLimitCnt())
                        .totalUsableCnt(wellnessTicketAdminDto.wellnessTicketDto().getTotalUsableCnt())
                        .usableDate(wellnessTicketAdminDto.wellnessTicketDto().getUsableDate())
                        .salesPrice(wellnessTicketAdminDto.wellnessTicketDto().getSalesPrice())
                        .price(wellnessTicketAdminDto.wellnessTicketDto().getPrice())
                        .discountValue(wellnessTicketAdminDto.wellnessTicketDto().getDiscountValue())
                        .isDelete(wellnessTicketAdminDto.wellnessTicketDto().getIsDelete())
                        .createdDate(wellnessTicketAdminDto.wellnessTicketDto().getCreatedDate().atZone(ZoneId.of("UTC")))
                        .build()
                )
                .toList();

        return ResponseEntity.ok(wellnessTicketAdminResponses);
    }

    @PostMapping("/v1/admin/wellness-ticket/{centerId}")
    public ResponseEntity<Boolean> createWellnessTicket(@PathVariable Long centerId, @RequestBody CreateWellnessTicketAdminRequestV1 createWellnessTicketAdminRequestV1) {
        createWellnessTicketAdminRequestV1.setCenterId(centerId);
        wellnessTicketAdminService.createWellnessTicket(createWellnessTicketAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/wellness-ticket/detail/{centerId}")
    public ResponseEntity<GetWellnessTicketDetailAdminResponseV1> getWellnessTicketDetailById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        final WellnessTicketAdminDto wellnessTicketAdminDto = wellnessTicketAdminService.getWellnessTicketDetailById(id);
        final GetWellnessTicketDetailAdminResponseV1 getWellnessTicketDetailAdminResponseV1 = GetWellnessTicketDetailAdminResponseV1.builder()
                .id(wellnessTicketAdminDto.wellnessTicketDto().getId())
                .type(wellnessTicketAdminDto.wellnessTicketDto().getType())
                .name(wellnessTicketAdminDto.wellnessTicketDto().getName())
                .backgroundColor(wellnessTicketAdminDto.wellnessTicketDto().getBackgroundColor())
                .textColor(wellnessTicketAdminDto.wellnessTicketDto().getTextColor())
                .price(wellnessTicketAdminDto.wellnessTicketDto().getPrice())
                .discountValue(wellnessTicketAdminDto.wellnessTicketDto().getDiscountValue())
                .limitType(wellnessTicketAdminDto.wellnessTicketDto().getLimitType())
                .limitCnt(wellnessTicketAdminDto.wellnessTicketDto().getLimitCnt())
                .totalUsableCnt(wellnessTicketAdminDto.wellnessTicketDto().getTotalUsableCnt())
                .usableDate(wellnessTicketAdminDto.wellnessTicketDto().getUsableDate())
                .salesPrice(wellnessTicketAdminDto.wellnessTicketDto().getSalesPrice())
                .isDelete(wellnessTicketAdminDto.wellnessTicketDto().getIsDelete())
                .createdDate(wellnessTicketAdminDto.wellnessTicketDto().getCreatedDate().atZone(ZoneId.of("UTC")))
                .wellnessClassNameList(
                        wellnessTicketAdminDto.wellnessClassDtoList().stream()
                                .map(wellnessClassDto -> GetWellnessTicketDetailAdminResponseV1.WellnessClassIdAndName.builder()
                                        .id(wellnessClassDto.getId())
                                        .name(wellnessClassDto.getName())
                                        .build())
                                .toList())
                .build();
        return ResponseEntity.ok(getWellnessTicketDetailAdminResponseV1);
    }

    @DeleteMapping("/v1/admin/wellness-ticket/{centerId}")
    public ResponseEntity<Boolean> deleteWellnessTicketById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        wellnessTicketAdminService.deleteWellnessTicketById(id);
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/v1/admin/wellness-ticket/restore/{centerId}")
    public ResponseEntity<Boolean> restoreWellnessTicketById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        wellnessTicketAdminService.restoreWellnessTicketById(id);
        return ResponseEntity.ok(true);
    }


    @PutMapping("/v1/admin/wellness-ticket/{centerId}")
    public ResponseEntity<Boolean> updateWellnessTicket(@PathVariable Long centerId, @RequestBody UpdateWellnessTicketAdminRequestV1 updateWellnessTicketAdminRequestV1) {
        updateWellnessTicketAdminRequestV1.setCenterId(centerId);
        wellnessTicketAdminService.updateWellnessTicket(updateWellnessTicketAdminRequestV1);
        return ResponseEntity.ok(true);
    }
}
