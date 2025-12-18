package com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDto {
    private String centerName;
    private String roleName;
    private String code;
    private String mobile;
    private String countryCode;
}
