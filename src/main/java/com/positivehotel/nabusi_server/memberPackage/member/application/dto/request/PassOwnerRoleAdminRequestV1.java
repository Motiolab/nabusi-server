package com.positivehotel.nabusi_server.memberPackage.member.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PassOwnerRoleAdminRequestV1 {
    private Long targetMemberId;
}
