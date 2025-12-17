package com.positivehotel.nabusi_server.socialUser.appleUser.application.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppleAuthTokenResponse {
    String access_token;
    String token_type;
    Integer expires_in;
    String refresh_token;
    String id_token;
}
