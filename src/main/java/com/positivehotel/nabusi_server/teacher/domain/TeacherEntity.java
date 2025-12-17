package com.positivehotel.nabusi_server.teacher.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="teacher")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickName;
    private String color;
    private String email;
    private String mobile;
    @Column(length = 2000)
    private String introduce;
    @Column(length = 2000)
    private String career;
    private Long centerId;
    private Long memberId;
    private Boolean useNickName;
    private String imageUrl;
    private Boolean isDelete;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static TeacherEntity create(final String name, final String nickName, final String color, final String email, final String mobile, final String introduce, final String career, final Long centerId, final Long memberId, final Boolean useNickName, final Boolean isDelete){
        return TeacherEntity.builder()
                .name(name)
                .nickName(nickName)
                .color(color)
                .email(email)
                .mobile(mobile)
                .introduce(introduce)
                .career(career)
                .memberId(memberId)
                .centerId(centerId)
                .useNickName(useNickName)
                .isDelete(isDelete)
                .build();
    }

    public void updateIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public void update(final String nickName, final String color, final String introduce, final String career, final Boolean useNickName, final String imageUrl, final Boolean isDelete){
        this.nickName = nickName;
        this.color = color;
        this.introduce = introduce;
        this.career = career;
        this.useNickName = useNickName;
        this.imageUrl = imageUrl;
        this.isDelete = isDelete;
    }
}
