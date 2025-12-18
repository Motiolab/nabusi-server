package com.motiolab.nabusi_server.security;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/v1/admin/login/success")
    public ResponseEntity<Boolean> getUser(OAuth2AuthenticationToken authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        final CustomOAuth2UserWithToken customOAuth2UserWithToken = (CustomOAuth2UserWithToken) authentication.getPrincipal();

        final String accessToken = customOAuth2UserWithToken.getAccessToken();
        final String refreshToken = customOAuth2UserWithToken.getRefreshToken();
        final ResponseCookie refreshTokenCookie = jwtTokenProvider.buildRefreshTokenCookie(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + accessToken)
                .header("Set-Cookie", refreshTokenCookie.toString())
                .body(true);
    }

    @GetMapping("/api/user")
    public ResponseEntity<Object> getUserInfo(@MemberId Long memberId) {
        return ResponseEntity.ok(memberId);
    }

    @GetMapping("/api/logout/success")
    public String logout() {
        return "Successfully logged out";
    }

    @PostMapping("/login/oauth2/code/apple")
    public void handleAppleLogin() {
    }

}