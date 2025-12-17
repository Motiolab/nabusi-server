package com.positivehotel.nabusi_server.socialUser.appleUser.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppleLoginMobileRequestV1 {
    private String authorizationCode;
}
