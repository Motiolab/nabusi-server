package com.positivehotel.nabusi_server.socialUser.naverToken.application.dto;

import com.positivehotel.nabusi_server.socialUser.naverToken.domain.NaverTokenEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class NaverTokenDto {
    private Long id;
    private String accessToken;
    private String expiresAt;
    private String refreshToken;
    private LocalDateTime issuedAt;
    private Long memberId;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime createdDate;

    public static NaverTokenDto from(NaverTokenEntity naverTokenEntity) {
        return NaverTokenDto.builder()
                .id(naverTokenEntity.getId())
                .accessToken(naverTokenEntity.getAccessToken())
                .expiresAt(naverTokenEntity.getExpiresAt())
                .refreshToken(naverTokenEntity.getRefreshToken())
                .issuedAt(naverTokenEntity.getIssuedAt())
                .memberId(naverTokenEntity.getMemberId())
                .lastUpdatedDate(naverTokenEntity.getLastUpdatedDate())
                .createdDate(naverTokenEntity.getCreatedDate())
                .build();
    }
}
