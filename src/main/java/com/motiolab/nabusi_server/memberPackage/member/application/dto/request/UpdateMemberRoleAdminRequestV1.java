package com.motiolab.nabusi_server.memberPackage.member.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateMemberRoleAdminRequestV1 {
    private Long memberId;
    private Long roleId;
}
