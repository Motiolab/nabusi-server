package com.positivehotel.nabusi_server.socialUser.naverUser.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverLoginMobileRequestV1 {
    private String naverAccessToken;
    private String naverAccessTokenExpireAt;
    private String refreshToken;
}
