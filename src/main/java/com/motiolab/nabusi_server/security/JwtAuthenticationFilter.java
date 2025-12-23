package com.motiolab.nabusi_server.security;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.urlPattern.application.dto.UrlPatternDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final DynamicRoleService dynamicRoleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthResponseHandler customAuthResponseHandler;

    public static final List<String> AUTH_WHITELIST = List.of("/v1/admin/login/success", "/api/user",
            "/v1/admin/member/.*", "/v1/mobile/kakao-user/login", "/v1/mobile/naver-user/login",
            "/v1/mobile/apple-user/login", "/v1/mobile/fcm-token-mobile", "/v1/admin/notification-fcm/test",
            "/v1/mobile/center/list", "/v1/mobile/wellness-lecture/list", "/v1/mobile/wellness-lecture/.*",
            "/v1/mobile/reservation/create", "/v1/mobile/reservation/cancel", "/v1/mobile/wellness-class/list",
            "/v1/mobile/teacher/list", "/v1/mobile/wellness-lecture/booking/date/.*",
            "/v1/mobile/wellness-lecture/schedule", "/v1/mobile/reservation/list",
            "/v1/mobile/reservation/list/check-in", "/s3/upload/mobile", "/v1/mobile/wellness-lecture-review/create",
            "/v1/mobile/wellness-lecture-review/.*", "/v1/mobile/my/wellness-ticket-issuance/list",
            "/v1/mobile/wellness-lecture-review/.*/.*", "/healthy/check", "/v1/mobile/auth/code",
            "/v1/verification/mobile/auth/code/.*", "/v1/mobile/apple-user/signup", "/favicon.ico", "/v1/mobile/member",
            "/v1/mobile/wellness-lecture/manage/date", "/v1/mobile/shop/.*",
            "/v3/api-docs.*", "/swagger-ui.*", "/swagger-ui.html"); // 테스트 ngrok 사용시 추가
                                                                    // "http://*.ngrok-free.app/login"
    public static final List<String> PERMISSION_WHITELIST = List.of("/v1/mobile/cart/item", "/v1/mobile/cart/item/.*",
            "/v1/mobile/cart/list",
            "/v1/admin/member/address/.*", "/v1/admin/member/point");

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info(
                "start ----------------------------------------------------------------------------------------------------------------------------------------------");
        log.info("SessionId : {}", request.getRequestedSessionId());
        log.info("ContextPath : {}", request.getContextPath());
        log.info("X-Real-IP: {}", request.getHeader("X-Real-IP"));
        log.info("X-Forwarded-For: {}", request.getHeader("X-Forwarded-For"));
        log.info("RequestIp: {}", request.getRemoteAddr());
        log.info("RequestURL: {}", request.getRequestURL());
        log.info("requestURI: {} {} ", request.getMethod(), request.getRequestURI());

        if (AUTH_WHITELIST.stream().anyMatch(url -> request.getRequestURI().matches(url))) {
            doFilter(request, response, filterChain);
            return;
        }

        final String authorization = request.getHeader("Authorization");
        final String accessToken = authorization != null ? authorization.replace("Bearer ", "") : null;
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            customAuthResponseHandler.unauthorized(response, "AccessToken is missing.");
            return;
        }

        if (!jwtTokenProvider.validateToken(accessToken)) {
            final String refreshToken = getRefreshTokenInCookies(request);
            final boolean isValidRefreshToken = jwtTokenProvider.validateToken(refreshToken);
            if (!isValidRefreshToken) {
                customAuthResponseHandler.unauthorized(response, "RefreshToken expired.");
                return;
            }
            jwtTokenProvider.refreshTokensAndAuthenticate(accessToken, response);
        }

        if (PERMISSION_WHITELIST.stream().noneMatch(url -> request.getRequestURI().matches(url))
                && !checkPermission(accessToken, request)) {
            customAuthResponseHandler.forbidden(response, "Permission denied.");
            return;
        }

        log.info(
                "end ------------------------------------------------------------------------------------------------------------------------------------------------");
        doFilter(request, response, filterChain);
    }

    private String getRefreshTokenInCookies(@org.springframework.lang.NonNull HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies() != null ? request.getCookies() : new Cookie[0];
        final List<Cookie> refreshTokenCookies = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("RefreshToken")).toList();
        return refreshTokenCookies.isEmpty() ? null : refreshTokenCookies.get(0).getValue();
    }

    public Boolean checkPermission(String accessToken, HttpServletRequest request) {
        final Long memberId = jwtTokenProvider.getMemberIdFromToken(accessToken);
        final MemberDto targetMemberDto = dynamicRoleService.getAllMemberDtoList()
                .stream()
                .filter(memberDto -> memberDto.getId().equals(memberId))
                .findFirst()
                .orElse(null);

        if (targetMemberDto == null) {
            return false;
        }

        final List<UrlPatternDto> urlPatternDtoList = targetMemberDto.getRoleList()
                .stream()
                .flatMap(roleDto -> roleDto.getUrlPatternDtoList().stream())
                .toList();

        if (request.getRequestURI().equals("/graphql")) {
            final String method = request.getParameter("method");
            final String action = request.getParameter("action");
            final Long centerId = Long.valueOf(request.getParameter("centerId"));

            return urlPatternDtoList.stream().anyMatch(urlPatternDto -> action.matches(urlPatternDto.getActionName())
                    && method.equals(urlPatternDto.getMethod()) && urlPatternDto.getCenterId().equals(centerId));
        } else {
            return urlPatternDtoList.stream()
                    .anyMatch(urlPatternDto -> {
                        String regex = urlPatternDto.getUrl().replace("*", ".*");
                        return request.getRequestURI().matches(regex)
                                && request.getMethod().equals(urlPatternDto.getMethod());
                    });
        }

    }
}
