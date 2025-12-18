package com.motiolab.nabusi_server.socialUser.naverUser.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "naver_user")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class NaverUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gender;
    private String email;
    private String mobile;
    @Column(unique = true, name = "mobile_e164")
    private String mobileE164;
    private String name;
    private String birthday;
    private String birthyear;
    private Long memberId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static NaverUserEntity create(final String gender, String email, final String mobile, final String mobileE164, final String name, final String birthday, final String birthyear, final Long memberId) {
        return NaverUserEntity.builder()
                .gender(gender)
                .email(email)
                .mobile(mobile)
                .mobileE164(mobileE164)
                .name(name)
                .birthday(birthday)
                .birthyear(birthyear)
                .memberId(memberId)
                .build();
    }

    public NaverUserEntity patch(final String gender, String email, final String mobile, final String mobileE164, final String name, final String birthday, final String birthyear, final Long memberId) {
        setGender(gender);
        setEmail(email);
        setMobile(mobile);
        setMobileE164(mobileE164);
        setName(name);
        setBirthday(birthday);
        setBirthyear(birthyear);
        setMemberId(memberId);
        return this;
    }
}
