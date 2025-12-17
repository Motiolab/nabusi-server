package com.positivehotel.nabusi_server.socialUser.appleUser.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "apple_user")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class AppleUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String email;
    private String mobile;
    private String nickName;
    private String fullName;
    private String sub;
    private Long memberId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static AppleUserEntity create(final String userId, final String email, final String mobile, final String nickName, final String fullName, final String sub, final Long memberId) {
        return AppleUserEntity.builder()
                .userId(userId)
                .email(email)
                .mobile(mobile)
                .nickName(nickName)
                .fullName(fullName)
                .sub(sub)
                .memberId(memberId)
                .build();
    }

    public AppleUserEntity patch(final String userId, final String email, final String mobile, final String nickName, final String fullName, final String sub, final Long memberId) {
        setUserId(userId);
        setEmail(email);
        setMobile(mobile);
        setNickName(nickName);
        setFullName(fullName);
        setSub(sub);
        setMemberId(memberId);
        return this;
    }
}
