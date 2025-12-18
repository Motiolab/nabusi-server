package com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true) // ✅ 추가 필드 무시
public class KakaoOAuth2Response {
    private Long id;
    private String connectedAt;
    private String synchedAt;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties {
        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {
        private boolean profileNeedsAgreement;
        private Profile profile;
        private boolean nameNeedsAgreement;
        private String name;
        private boolean hasEmail;
        private boolean emailNeedsAgreement;
        private boolean isEmailValid;
        private boolean isEmailVerified;
        private String email;
        private boolean hasPhoneNumber;
        private boolean phoneNumberNeedsAgreement;
        private String phoneNumber;
        private boolean hasAgeRange;
        private boolean ageRangeNeedsAgreement;
        private String ageRange;
        private boolean hasBirthyear;
        private boolean birthyearNeedsAgreement;
        private String birthyear;
        private boolean hasBirthday;
        private boolean birthdayNeedsAgreement;
        private String birthday;
        private String birthdayType;
        private boolean hasGender;
        private boolean genderNeedsAgreement;
        private String gender;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Profile {
            private String nickname;
            private String thumbnailImageUrl;
            private String profileImageUrl;
            private boolean isDefaultImage;
            private boolean isDefaultNickname;
        }
    }
}
