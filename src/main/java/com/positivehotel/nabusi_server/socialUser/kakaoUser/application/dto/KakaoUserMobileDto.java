package com.positivehotel.nabusi_server.socialUser.kakaoUser.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoUserMobileDto {
    private String accessToken;
    private String refreshToken;
}
