package com.positivehotel.nabusi_server.role.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleUrlPatternRequestV1 {
    String roleName;
}
