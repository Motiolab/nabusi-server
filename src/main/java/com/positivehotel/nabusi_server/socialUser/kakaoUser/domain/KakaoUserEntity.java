package com.positivehotel.nabusi_server.socialUser.kakaoUser.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "kakao_user")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String email;
    private String birthDay;
    private String birthYear;
    private String gender;
    @Column(unique = true)
    private String phoneNumber;
    private String name;
    private String nickName;
    private Long memberId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static KakaoUserEntity create(final Long userId, final String email, final String birthDay, final String birthYear, final String gender, final String phoneNumber, final String name, final String nickName, final Long memberId) {
        return KakaoUserEntity.builder().userId(userId).email(email).birthDay(birthDay).birthYear(birthYear).gender(gender).phoneNumber(phoneNumber).name(name).nickName(nickName).memberId(memberId).build();
    }

    public KakaoUserEntity patch(final Long userId, final String email, final String birthDay, final String birthYear, final String gender, final String phoneNumber, final String name, final String nickName, final Long memberId) {
        setUserId(userId);
        setEmail(email);
        setBirthDay(birthDay);
        setBirthYear(birthYear);
        setGender(gender);
        setPhoneNumber(phoneNumber);
        setName(name);
        setNickName(nickName);
        setMemberId(memberId);
        return this;
    }
}
