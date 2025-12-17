package com.positivehotel.nabusi_server.socialUser.kakaoUser.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoLoginMobileRequestV1 {
    private String kakaoAccessToken;
}
