package com.positivehotel.nabusi_server.socialUser.kakaoUser.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.positivehotel.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserMobileDto;

public interface KakaoUserMobileService {
    KakaoUserMobileDto kakaoLogin(String kakaoAccessToken) throws JsonProcessingException;
    String delete(Long memberId);
}
