package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="wellness_lecture_review")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WellnessLectureReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer rating;
    private Long wellnessLectureId;
    private Long wellnessClassId;
    private Long memberId;
    private Long teacherId;
    private Long centerId;
    private String content;
    private Boolean isPrivate;
    private Boolean isDelete;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public static WellnessLectureReviewEntity create(final Integer rating, final Long wellnessLectureId, final Long wellnessClassId, final Long memberId, final Long teacherId, final Long centerId, final String content, final Boolean isPrivate, final Boolean isDelete) {
        return WellnessLectureReviewEntity.builder()
                .rating(rating)
                .wellnessLectureId(wellnessLectureId)
                .wellnessClassId(wellnessClassId)
                .memberId(memberId)
                .teacherId(teacherId)
                .centerId(centerId)
                .content(content)
                .isPrivate(isPrivate)
                .isDelete(isDelete)
                .build();
    }

    public void updateIsDelete(Boolean isDelete) {
        if(isDelete != null) setIsDelete(isDelete);
    }

    public void update(String content, Integer rating, Boolean isPrivate) {
        if(content != null) setContent(content);
        if(rating != null) setRating(rating);
        if(isPrivate != null) setIsPrivate(isPrivate);
    }
}
