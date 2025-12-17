package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application;

import com.positivehotel.nabusi_server.memberPackage.member.application.MemberService;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.WellnessTicketExtensionAdminDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.WellnessTicketExtensionDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto.request.CreateWellnessTicketExtensionAdminRequestV1;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessTicketExtensionAdminServiceImpl implements WellnessTicketExtensionAdminService{
    private final WellnessTicketExtensionService wellnessTicketExtensionService;
    private final MemberService memberService;
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;

    @Transactional
    @Override
    public void createWellnessTicketExtension(CreateWellnessTicketExtensionAdminRequestV1 createWellnessTicketExtensionAdminRequestV1) {
        final WellnessTicketExtensionDto wellnessTicketExtensionDto = WellnessTicketExtensionDto.builder()
                .extendDate(createWellnessTicketExtensionAdminRequestV1.getExtendDate())
                .isApplyAfter(createWellnessTicketExtensionAdminRequestV1.getIsApplyAfter())
                .registerId(createWellnessTicketExtensionAdminRequestV1.getRegisterId())
                .reason(createWellnessTicketExtensionAdminRequestV1.getReason())
                .targetDate(createWellnessTicketExtensionAdminRequestV1.getTargetDate())
                .wellnessTicketId(createWellnessTicketExtensionAdminRequestV1.getWellnessTicketId())
                .build();

        wellnessTicketExtensionService.create(wellnessTicketExtensionDto);

        final List<WellnessTicketIssuanceDto> wellnessTicketIssuanceDtoList = wellnessTicketIssuanceService.getAllByWellnessTicketId(createWellnessTicketExtensionAdminRequestV1.getWellnessTicketId());
        wellnessTicketIssuanceDtoList.forEach(wellnessTicketIssuanceDto -> {
            final ZonedDateTime expireDate = wellnessTicketIssuanceDto.getExpireDate();
            final ZonedDateTime expireExtensionDate = expireDate.plusDays(createWellnessTicketExtensionAdminRequestV1.getExtendDate());
            wellnessTicketIssuanceDto.setExpireDate(expireExtensionDate);
            wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
        });
    }

    @Override
    public List<WellnessTicketExtensionAdminDto> getWellnessTicketExtensionListByWellnessTicketId(Long wellnessTicketId) {
        final List<WellnessTicketExtensionDto> wellnessTicketExtensionDtoList = wellnessTicketExtensionService.getAllByWellnessTicketId(wellnessTicketId);
        final List<Long> memberIdList = wellnessTicketExtensionDtoList.stream().map(WellnessTicketExtensionDto::getRegisterId).toList();
        final List<MemberDto> memberDtoList =  memberService.getAllByIdList(memberIdList);
        return wellnessTicketExtensionDtoList
                .stream()
                .map(wellnessTicketExtensionDto -> WellnessTicketExtensionAdminDto.builder()
                        .wellnessTicketExtensionDto(wellnessTicketExtensionDto)
                        .registerMemberDto(memberDtoList.stream().filter(memberDto -> memberDto.getId().equals(wellnessTicketExtensionDto.getRegisterId())).findFirst().orElse(null))
                        .build()
                ).toList();
    }
}
