package com.motiolab.nabusi_server.notificationPackage.notificationFcm.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendNotificationFcmTestRequestV1 {
    private String fcmToken;
    private String title;
    private String body;
}
