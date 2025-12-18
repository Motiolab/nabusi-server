package com.motiolab.nabusi_server.socialUser.appleUser.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AppleUserMobileDto {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String token;
    private String message;
    private String sub;
    private Boolean isSuccessLogin;
}
