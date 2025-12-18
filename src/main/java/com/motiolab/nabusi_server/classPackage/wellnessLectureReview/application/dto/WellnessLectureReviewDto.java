package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.domain.WellnessLectureReviewEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class WellnessLectureReviewDto {
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
    private ZonedDateTime createdDate;
    private ZonedDateTime lastUpdatedDate;

    public static WellnessLectureReviewDto from(final WellnessLectureReviewEntity wellnessLectureReviewEntity) {
        return WellnessLectureReviewDto.builder()
                .id(wellnessLectureReviewEntity.getId())
                .rating(wellnessLectureReviewEntity.getRating())
                .wellnessLectureId(wellnessLectureReviewEntity.getWellnessLectureId())
                .wellnessClassId(wellnessLectureReviewEntity.getWellnessClassId())
                .memberId(wellnessLectureReviewEntity.getMemberId())
                .teacherId(wellnessLectureReviewEntity.getTeacherId())
                .centerId(wellnessLectureReviewEntity.getCenterId())
                .content(wellnessLectureReviewEntity.getContent())
                .isPrivate(wellnessLectureReviewEntity.getIsPrivate())
                .isDelete(wellnessLectureReviewEntity.getIsDelete())
                .createdDate(wellnessLectureReviewEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .lastUpdatedDate(wellnessLectureReviewEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
