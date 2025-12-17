package com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetWellnessLectureReviewByIdMobileResponse {
    private Long id;
    private String content;
    private Boolean isPrivate;
    private Integer rating;
}
