package com.positivehotel.nabusi_server.socialUser.appleUser.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppleSignupDto {
    private String accessToken;
    private String refreshToken;
}
