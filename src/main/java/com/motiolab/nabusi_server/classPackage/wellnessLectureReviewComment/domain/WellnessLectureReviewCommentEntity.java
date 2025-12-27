package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "wellness_lecture_review_comment")
@Where(clause = "is_delete = false")
@SQLDelete(sql = "UPDATE wellness_lecture_review_comment SET is_delete = true WHERE id = ?")
public class WellnessLectureReviewCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wellness_lecture_review_id")
    private Long wellnessLectureReviewId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    public static WellnessLectureReviewCommentEntity create(Long wellnessLectureReviewId, Long memberId,
            String content) {
        return WellnessLectureReviewCommentEntity.builder()
                .wellnessLectureReviewId(wellnessLectureReviewId)
                .memberId(memberId)
                .content(content)
                .isDelete(false)
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .build();
    }
}
