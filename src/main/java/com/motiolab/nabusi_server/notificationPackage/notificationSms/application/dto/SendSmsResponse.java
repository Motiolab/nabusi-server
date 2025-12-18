package com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSmsResponse {
    private String requestId;
    private String requestTime;
    private String statusCode;
    private String statusName;
}
