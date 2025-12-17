package com.positivehotel.nabusi_server;

import com.positivehotel.nabusi_server.socialUser.appleUser.application.AppleSecretGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthorizationCodeGrantRequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter {
    private static final String APPLE_REGISTRATION_ID = "apple";
    private static final String CLIENT_SECRET_KEY = "client_secret";

    private final AppleSecretGenerator appleSecretGenerator;

    @Override
    protected MultiValueMap<String, String> createParameters(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        final String clientRegistrationId = authorizationCodeGrantRequest.getClientRegistration().getRegistrationId();
        if (APPLE_REGISTRATION_ID.equalsIgnoreCase(clientRegistrationId)) {
            final String encryptedPrivateKey = appleSecretGenerator.createClientSecret();
            final MultiValueMap<String, String> parameter = super.createParameters(authorizationCodeGrantRequest);
            parameter.put(CLIENT_SECRET_KEY, List.of(encryptedPrivateKey));
            return parameter;
        }

        return super.createParameters(authorizationCodeGrantRequest);
    }
}
