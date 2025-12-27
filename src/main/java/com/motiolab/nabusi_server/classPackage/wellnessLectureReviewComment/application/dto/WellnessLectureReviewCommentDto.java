package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.domain.WellnessLectureReviewCommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WellnessLectureReviewCommentDto {
    private Long id;
    private Long wellnessLectureReviewId;
    private Long memberId;
    private String content;
    private Boolean isDelete;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    public static WellnessLectureReviewCommentDto from(WellnessLectureReviewCommentEntity entity) {
        return WellnessLectureReviewCommentDto.builder()
                .id(entity.getId())
                .wellnessLectureReviewId(entity.getWellnessLectureReviewId())
                .memberId(entity.getMemberId())
                .content(entity.getContent())
                .isDelete(entity.getIsDelete())
                .createdDate(entity.getCreatedDate())
                .lastUpdatedDate(entity.getLastUpdatedDate())
                .build();
    }
}
