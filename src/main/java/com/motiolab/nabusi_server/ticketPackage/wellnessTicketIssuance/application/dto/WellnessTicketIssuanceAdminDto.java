package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WellnessTicketIssuanceAdminDto {
    private WellnessTicketIssuanceDto wellnessTicketIssuanceDto;
    private MemberDto memberDto;
}
