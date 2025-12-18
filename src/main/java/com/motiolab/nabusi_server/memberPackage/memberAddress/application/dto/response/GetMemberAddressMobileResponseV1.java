package com.motiolab.nabusi_server.memberPackage.memberAddress.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetMemberAddressMobileResponseV1 {
    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private String detailAddress;
    private String recipient;
    private String mobile;
    private String zipCode;
    private String roadName;
    private String isDefault;
}
