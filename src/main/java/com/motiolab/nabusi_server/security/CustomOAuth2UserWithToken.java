package com.motiolab.nabusi_server.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2UserWithToken extends DefaultOAuth2User {
    private final String accessToken;
    private final String refreshToken;
    public CustomOAuth2UserWithToken(Collection<? extends GrantedAuthority> authorities,
                                     Map<String, Object> attributes,
                                     String nameAttributeKey,
                                     String accessToken,
                                     String refreshToken) {
        super(authorities, attributes, nameAttributeKey);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}