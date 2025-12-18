package com.motiolab.nabusi_server.memberPackage.memberPoint.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetMemberPointMobileResponseV1 {
    private Long currentPoint;
}
