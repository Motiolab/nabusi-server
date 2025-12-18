package com.motiolab.nabusi_server.role.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateUrlPatternActionRequestV1 {
    String roleName;
    String actionName;
}
