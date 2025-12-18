package com.motiolab.nabusi_server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Autowired
    private CustomAuthResponseHandler customAuthResponseHandler;

    private byte[] getDecodedSecretKey() {
        return java.util.Base64.getDecoder().decode(secretKey);
    }

    public String generateToken(long expirationTime) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(getDecodedSecretKey()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(Long memberId, long expirationTime) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(getDecodedSecretKey()), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            String parsedToken = token.replace("Bearer ", "");
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(getDecodedSecretKey()))
                    .build()
                    .parseClaimsJws(parsedToken);
            return true;
        } catch (Exception e) {
            log.error("{}", e.getClass().getName(), e);
            return false;
        }
    }

    public String generateAccessToken(Long memberId) {
        return generateToken(memberId, accessTokenExpiration);
    }

    public String generateRefreshToken() {
        return generateToken(refreshTokenExpiration);
    }

    public Long getMemberIdFromToken(String token) {
        final Claims claims = getClaims(token);
        return claims.get("memberId", Long.class);
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(getDecodedSecretKey()))
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public void refreshTokensAndAuthenticate(String accessToken,
            HttpServletResponse response) {
        final Long memberId = getMemberIdFromToken(accessToken);

        final String newAccessToken = generateAccessToken(memberId);
        final String refreshToken = generateRefreshToken();
        final ResponseCookie cookie = buildRefreshTokenCookie(refreshToken);

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.setHeader("Set-Cookie", cookie.toString());
    }

    public ResponseCookie buildRefreshTokenCookie(final String refreshToken) {
        return ResponseCookie.from("RefreshToken", refreshToken)
                .maxAge(refreshTokenExpiration / 1000) // 90 days
                .path("/")
                // .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
    }
}