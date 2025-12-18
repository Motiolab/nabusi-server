package com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.application.dto;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WellnessTicketExtensionAdminDto {
    WellnessTicketExtensionDto wellnessTicketExtensionDto;
    MemberDto registerMemberDto;
}
