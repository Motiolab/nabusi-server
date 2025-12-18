package com.motiolab.nabusi_server.invitationAdminMember.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetInviteAdminMemberToMeResponseV1 {
    private Long invitationAdminMemberId;
    private String memberName;
    private Long centerId;
    private String sendAdminMemberName;
    private Long roleId;
    private String centerName;
    private String centerAddress;
    private String centerDetailAddress;
}
