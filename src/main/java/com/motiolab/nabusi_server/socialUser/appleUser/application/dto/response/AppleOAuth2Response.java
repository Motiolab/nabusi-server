package com.motiolab.nabusi_server.socialUser.appleUser.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppleOAuth2Response {
    final String atHash;
    final String aud;
    final String sub;
    final Boolean nonceSupported;
    final Integer authTime;
    final String iss;
    final Integer exp;
    final Integer iat;
}
