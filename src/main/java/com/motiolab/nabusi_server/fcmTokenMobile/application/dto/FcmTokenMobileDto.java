package com.motiolab.nabusi_server.fcmTokenMobile.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FcmTokenMobileDto {
    private Long id;
    private String token;
    private Long memberId;
}
