package com.motiolab.nabusi_server.socialUser.appleUser.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppleSignupMobileRequestV1 {
    private String userId;
    private String name;
    private String mobile;
    private String countryCode;
    private String sub;
}
