package com.motiolab.nabusi_server.socialUser.naverUser.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NaverUserMobileDto {
    private String accessToken;
    private String refreshToken;
}
