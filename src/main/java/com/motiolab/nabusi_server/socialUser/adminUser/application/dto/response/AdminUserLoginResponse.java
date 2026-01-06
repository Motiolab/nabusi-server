package com.motiolab.nabusi_server.socialUser.adminUser.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AdminUserLoginResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private Boolean isSuccessLogin;
    private String message;
}
