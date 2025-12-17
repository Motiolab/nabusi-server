package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.ui;

import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceMobileService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceMobileDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.response.GetMyWellnessTicketIssuanceListMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WellnessTicketIssuanceMobileController {
    private final WellnessTicketIssuanceMobileService wellnessTicketIssuanceAdminService;


    @GetMapping("/v1/mobile/my/wellness-ticket-issuance/list")
    public ResponseEntity<List<GetMyWellnessTicketIssuanceListMobileResponse>> getMyWellnessTicketIssuanceList(@MemberId Long memberId) {
        final List<WellnessTicketIssuanceMobileDto> wellnessTicketIssuanceMobileDtoList = wellnessTicketIssuanceAdminService.getMyWellnessTicketIssuanceList(memberId);

        final List<GetMyWellnessTicketIssuanceListMobileResponse> getMyWellnessTicketIssuanceListMobileResponseList = wellnessTicketIssuanceMobileDtoList
                .stream()
                .map(wellnessTicketIssuanceMobileDto -> {
                    final ZonedDateTime now = ZonedDateTime.now();
                    final ZonedDateTime expireDate = wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getExpireDate();
                    final long remainingDate = Duration.between(now, expireDate).toDays();
                    return GetMyWellnessTicketIssuanceListMobileResponse.builder()
                            .id(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getId())
                            .wellnessTicketIssuanceName(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getName())
                            .type(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getType())
                            .backgroundColor(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getBackgroundColor())
                            .remainingCnt(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getRemainingCnt())
                            .remainingDate(remainingDate)
                            .totalUsableCnt(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getTotalUsableCnt())
                            .startDate(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getStartDate())
                            .expireDate(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getExpireDate())
                            .isDelete(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getIsDelete())
                            .unpaidValue(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getUnpaidValue())
                            .limitType(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getLimitType())
                            .limitCnt(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getLimitCnt())
                            .createdDateTime(wellnessTicketIssuanceMobileDto.getWellnessTicketIssuanceDto().getCreatedDate())
                            .build();
                })
                .toList();

        return ResponseEntity.ok(getMyWellnessTicketIssuanceListMobileResponseList);
    }
}
