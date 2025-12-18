package com.motiolab.nabusi_server.centerPackage.center.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMyCenterListByMemberIdResponseV1 {
    private Long id;
    private String name;
    private String address;
    private String detailAddress;
    private String roadName;
    private String roleName;
    private String code;
    private Boolean isActive;
}
