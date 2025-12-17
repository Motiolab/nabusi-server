package com.positivehotel.nabusi_server.invitationAdminMember.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InviteAdminMemberRequestV1 {
    private String mobile;
    private Long roleId;
}
