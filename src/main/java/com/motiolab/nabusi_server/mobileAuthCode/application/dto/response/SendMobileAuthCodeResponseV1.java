package com.motiolab.nabusi_server.mobileAuthCode.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMobileAuthCodeResponseV1 {
    private String requestId;
    private String requestTime;
    private String statusCode;
    private String statusName;
}
