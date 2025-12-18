package com.motiolab.nabusi_server.memberPackage.member.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMemberListByCenterIdAdminResponseV1 {
    Long memberId;
    String name;
    String mobile;
    String email;
    String roleName;
    Long roleId;
}
