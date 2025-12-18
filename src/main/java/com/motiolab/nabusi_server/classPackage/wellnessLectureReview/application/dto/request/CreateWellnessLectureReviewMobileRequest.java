package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateWellnessLectureReviewMobileRequest {
    private Long wellnessLectureId;
    private String content;
    private Integer rating;
    private Boolean isPrivate;
}
