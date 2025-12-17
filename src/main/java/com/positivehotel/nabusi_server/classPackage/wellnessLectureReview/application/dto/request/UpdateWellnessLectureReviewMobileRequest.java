package com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateWellnessLectureReviewMobileRequest {
    private Long wellnessLectureReviewId;
    private String content;
    private Integer rating;
    private Boolean isPrivate;
}
