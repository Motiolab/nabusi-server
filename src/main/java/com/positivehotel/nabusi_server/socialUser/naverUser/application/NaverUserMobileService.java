package com.positivehotel.nabusi_server.socialUser.naverUser.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.positivehotel.nabusi_server.socialUser.naverUser.application.dto.NaverUserMobileDto;
import com.positivehotel.nabusi_server.socialUser.naverUser.application.dto.request.NaverLoginMobileRequestV1;

public interface NaverUserMobileService {
    NaverUserMobileDto naverLogin(NaverLoginMobileRequestV1 naverLoginMobileRequestV1) throws JsonProcessingException;
    void resignNaverUser(String accessToken);
}
