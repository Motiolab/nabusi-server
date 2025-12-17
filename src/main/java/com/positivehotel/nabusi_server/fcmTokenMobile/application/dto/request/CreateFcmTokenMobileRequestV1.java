package com.positivehotel.nabusi_server.fcmTokenMobile.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFcmTokenMobileRequestV1 {
    private String fcmToken;
}
