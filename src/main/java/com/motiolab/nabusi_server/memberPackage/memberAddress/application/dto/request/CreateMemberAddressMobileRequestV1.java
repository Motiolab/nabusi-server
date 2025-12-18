package com.motiolab.nabusi_server.memberPackage.memberAddress.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateMemberAddressMobileRequestV1 {
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
