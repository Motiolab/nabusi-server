package com.positivehotel.nabusi_server.invitationAdminMember.application.dto;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InvitationAdminMemberAdminDto {
    InvitationAdminMemberDto invitationAdminMemberDto;
    MemberDto sendAdminMemberDto;
    MemberDto myMemberDto;
    CenterDto centerDto;
}
