package com.positivehotel.nabusi_server.role.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetRoleInfoByCenterIdResponseV1 {
    private Long id;
    private String name;
}
