package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceAdminService;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceAdminDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.request.CreateWellnessTicketIssuanceAdminRequestV1;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.request.UpdateWellnessTicketIssuanceAdminRequestV1;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.response.GetWellnessTicketIssuanceDetailByIdAdminResponseV1;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.response.GetWellnessTicketIssuanceListByWellnessTicketIdAdminResponseV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessTicketIssuanceAdminController {
    private final WellnessTicketIssuanceAdminService wellnessTicketIssuanceAdminService;

    @PostMapping("/v1/admin/wellness-ticket-issuance/{centerId}")
    public ResponseEntity<Boolean> createWellnessTicketIssuance(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody CreateWellnessTicketIssuanceAdminRequestV1 createWellnessTicketIssuanceAdminRequestV1) {
        createWellnessTicketIssuanceAdminRequestV1.setCenterId(centerId);
        createWellnessTicketIssuanceAdminRequestV1.setActionMemberId(memberId);
        wellnessTicketIssuanceAdminService.createWellnessTicketIssuance(createWellnessTicketIssuanceAdminRequestV1);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/v1/admin/wellness-ticket-issuance/list/wellness-ticket-id/{centerId}")
    public ResponseEntity<List<GetWellnessTicketIssuanceListByWellnessTicketIdAdminResponseV1>> getWellnessTicketIssuanceListByWellnessTicketId(@MemberId Long memberId, @PathVariable Long centerId, @RequestParam(defaultValue = "wellnessTicketId") Long wellnessTicketId) {
        final List<WellnessTicketIssuanceAdminDto> wellnessTicketIssuanceAdminDtoList = wellnessTicketIssuanceAdminService.getWellnessTicketIssuanceListByWellnessTicketId(wellnessTicketId);
        final List<GetWellnessTicketIssuanceListByWellnessTicketIdAdminResponseV1> responseList = wellnessTicketIssuanceAdminDtoList
                .stream()
                .map(wellnessTicketIssuanceAdminDto -> GetWellnessTicketIssuanceListByWellnessTicketIdAdminResponseV1.builder()
                        .id(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getId())
                        .wellnessTicketIssuanceName(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getName())
                        .memberName(wellnessTicketIssuanceAdminDto.getMemberDto().getName())
                        .mobile(wellnessTicketIssuanceAdminDto.getMemberDto().getMobile())
                        .type(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getType())
                        .remainingCnt(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getRemainingCnt())
                        .remainingDate(Duration.between(ZonedDateTime.now(), wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getExpireDate()).toDays())
                        .totalUsableCnt(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getTotalUsableCnt())
                        .startDate(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getStartDate())
                        .expireDate(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getExpireDate())
                        .isDelete(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getIsDelete())
                        .unpaidValue(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getUnpaidValue())
                        .limitType(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getLimitType())
                        .limitCnt(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getLimitCnt())
                        .createdDateTime(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getCreatedDate())
                        .build())
                .toList()
                .stream()
                .sorted(Comparator.comparing(GetWellnessTicketIssuanceListByWellnessTicketIdAdminResponseV1::getCreatedDateTime).reversed())
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/v1/admin/wellness-ticket-issuance/update/detail/{centerId}")
    public ResponseEntity<GetWellnessTicketIssuanceDetailByIdAdminResponseV1> getWellnessTicketIssuanceUpdateDetailById(@PathVariable Long centerId, @RequestParam(defaultValue = "id") Long id) {
        final WellnessTicketIssuanceAdminDto wellnessTicketIssuanceAdminDto = wellnessTicketIssuanceAdminService.getWellnessTicketIssuanceUpdateDetailById(id);

        final GetWellnessTicketIssuanceDetailByIdAdminResponseV1 response = GetWellnessTicketIssuanceDetailByIdAdminResponseV1.builder()
                .id(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getId())
                .memberId(wellnessTicketIssuanceAdminDto.getMemberDto().getId())
                .memberName(wellnessTicketIssuanceAdminDto.getMemberDto().getName())
                .mobile(wellnessTicketIssuanceAdminDto.getMemberDto().getMobile())
                .ticketName(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getName())
                .startDate(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getStartDate())
                .expireDate(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getExpireDate())
                .type(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getType())
                .backgroundColor(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getBackgroundColor())
                .totalUsableCnt(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getTotalUsableCnt())
                .limitType(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getLimitType())
                .limitCnt(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getLimitCnt())
                .unpaidValue(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getUnpaidValue())
                .remainingCnt(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getRemainingCnt())
                .wellnessTicketId(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getWellnessTicketId())
                .isDelete(wellnessTicketIssuanceAdminDto.getWellnessTicketIssuanceDto().getIsDelete())
                .build();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/v1/admin/wellness-ticket-issuance/update/{centerId}")
    public ResponseEntity<Boolean> updateWellnessTicketIssuance(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody UpdateWellnessTicketIssuanceAdminRequestV1 updateWellnessTicketIssuanceAdminRequestV1) {
        updateWellnessTicketIssuanceAdminRequestV1.setActionMemberId(memberId);
        wellnessTicketIssuanceAdminService.updateWellnessTicketIssuance(updateWellnessTicketIssuanceAdminRequestV1);
        return ResponseEntity.ok(true);
    }
}
