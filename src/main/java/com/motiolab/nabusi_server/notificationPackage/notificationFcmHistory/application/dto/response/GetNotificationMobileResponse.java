package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class GetNotificationMobileResponse {
    private Long id;
    private String title;
    private String body;
    private String detail;
    private ZonedDateTime createdDate;
}
