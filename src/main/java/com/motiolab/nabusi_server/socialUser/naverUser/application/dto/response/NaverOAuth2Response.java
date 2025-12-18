package com.motiolab.nabusi_server.socialUser.naverUser.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverOAuth2Response {
    private String resultcode;
    private String message;
    private Response response;

    @Data
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Response {
        private String id;
        private String gender;
        private String email;
        private String mobile;
        private String nickname;

        @JsonProperty("mobile_e164")
        private String mobileE164;

        private String name;
        private String birthday;
        private String birthyear;
        private String age;
    }
}
