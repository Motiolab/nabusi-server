package com.motiolab.nabusi_server.mobileAuthCode.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="mobile_auth_code")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class MobileAuthCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String countryCode;
    private String mobile;
    private String authCode;
    private LocalDateTime expireDateTime;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static MobileAuthCodeEntity create(String countryCode, String mobile, String authCode, LocalDateTime expireDateTime) {
        return MobileAuthCodeEntity.builder()
                .countryCode(countryCode)
                .mobile(mobile)
                .authCode(authCode)
                .expireDateTime(expireDateTime)
                .build();
    }
}
