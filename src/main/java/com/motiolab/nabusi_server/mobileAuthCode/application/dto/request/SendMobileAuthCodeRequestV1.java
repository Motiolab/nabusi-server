package com.motiolab.nabusi_server.mobileAuthCode.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMobileAuthCodeRequestV1 {
    private String countryCode;
    private String mobile;
}
