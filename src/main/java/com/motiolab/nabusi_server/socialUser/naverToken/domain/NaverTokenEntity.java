package com.motiolab.nabusi_server.socialUser.naverToken.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "naver_token")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class NaverTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;
    private String expiresAt;
    private String refreshToken;
    private LocalDateTime issuedAt;
    private Long memberId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static NaverTokenEntity create(final String accessToken, final String expiresAt, final String refreshToken,  final LocalDateTime issuedAt, final Long memberId) {
        return NaverTokenEntity.builder()
                .accessToken(accessToken)
                .expiresAt(expiresAt)
                .refreshToken(refreshToken)
                .issuedAt(issuedAt)
                .memberId(memberId)
                .build();
    }

    public void update(final String accessToken, final String expiresAt, final String refreshToken, final LocalDateTime issuedAt, final Long memberId) {
        if(accessToken != null) setAccessToken(accessToken);
        if(expiresAt != null) setExpiresAt(expiresAt);
        if(refreshToken != null) setRefreshToken(refreshToken);
        if(issuedAt != null) setIssuedAt(issuedAt);
        if(memberId != null) setMemberId(memberId);
    }

    public NaverTokenEntity patch(final String accessToken, final String expiresAt, final String refreshToken, final LocalDateTime issuedAt, final Long memberId) {
        setAccessToken(accessToken);
        setExpiresAt(expiresAt);
        setRefreshToken(refreshToken);
        setIssuedAt(issuedAt);
        setMemberId(memberId);
        return this;
    }
}
