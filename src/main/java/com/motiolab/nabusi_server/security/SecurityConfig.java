package com.motiolab.nabusi_server.security;

import com.motiolab.nabusi_server.CustomOAuth2AuthorizationCodeGrantRequestEntityConverter;
import com.motiolab.nabusi_server.socialUser.appleUser.application.AppleSecretGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final DynamicRoleService dynamicRoleService;
    private final CustomAuthResponseHandler customAuthResponseHandler;
    private final AppleSecretGenerator appleSecretGenerator;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService,
            JwtTokenProvider jwtTokenProvider) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .tokenEndpoint(tokenEndpoint -> {
                            DefaultAuthorizationCodeTokenResponseClient defaultAuthorizationCodeTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
                            defaultAuthorizationCodeTokenResponseClient.setRequestEntityConverter(
                                    new CustomOAuth2AuthorizationCodeGrantRequestEntityConverter(appleSecretGenerator));
                            tokenEndpoint.accessTokenResponseClient(defaultAuthorizationCodeTokenResponseClient);
                        })
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        // .successHandler((request, response, authentication) ->
                        // response.sendRedirect("https://admin.motiolab.com/login/success"))
                        .successHandler((request, response, authentication) -> response
                                .sendRedirect("http://localhost:3000/login/success")))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(
                                (request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK)));
        // .addFilterBefore(new JwtAuthenticationFilter(dynamicRoleService,
        // jwtTokenProvider, customAuthResponseHandler),
        // UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "https://nabusi.com",
                "https://appleid.apple.com", "http://localhost:8080", "http://127.0.0.1:3000", "http://10.0.2.2:3000",
                "https://admin.nabusi.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html");
    }
}