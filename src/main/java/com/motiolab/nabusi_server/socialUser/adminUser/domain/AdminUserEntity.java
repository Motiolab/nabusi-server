package com.motiolab.nabusi_server.socialUser.adminUser.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "admin_user")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String birthYear;
    private String birthDay;
    private String gender;
    private String phoneNumber;

    @Column(nullable = false)
    private Long memberId;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static AdminUserEntity create(String email, String password, String birthYear, String birthDay,
            String gender, String phoneNumber, Long memberId) {
        return AdminUserEntity.builder()
                .email(email)
                .password(password)
                .birthYear(birthYear)
                .birthDay(birthDay)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .memberId(memberId)
                .build();
    }
}
