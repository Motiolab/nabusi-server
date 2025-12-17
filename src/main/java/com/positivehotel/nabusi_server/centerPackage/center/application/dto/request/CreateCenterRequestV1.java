package com.positivehotel.nabusi_server.centerPackage.center.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateCenterRequestV1 {
    String name;
    String address;
    String code;
    String detailAddress;
    String roadName;
}
