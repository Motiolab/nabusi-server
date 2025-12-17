package com.positivehotel.nabusi_server.fcmTokenMobile.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "fcm_token_mobile")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenMobileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Long memberId;
    private Boolean isLogin;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static FcmTokenMobileEntity create(String token, Long memberId, Boolean isLogin) {
        return FcmTokenMobileEntity.builder()
                .token(token)
                .memberId(memberId)
                .isLogin(isLogin)
                .build();
    }

    public void update(String token, Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }

    public void updateIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }
}
