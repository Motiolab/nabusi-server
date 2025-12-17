package com.positivehotel.nabusi_server.socialUser.appleUser.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.positivehotel.nabusi_server.socialUser.appleUser.application.dto.AppleSignupDto;
import com.positivehotel.nabusi_server.socialUser.appleUser.application.dto.AppleUserMobileDto;
import com.positivehotel.nabusi_server.socialUser.appleUser.application.dto.request.AppleSignupMobileRequestV1;

public interface AppleUserMobileService {
    AppleUserMobileDto appleLogin(String appleAccessToken) throws JsonProcessingException;
    AppleSignupDto appleSignup(AppleSignupMobileRequestV1 appleSignupMobileRequestV1);
}
