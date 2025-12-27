package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateWellnessLectureReviewCommentMobileRequest {
    private Long wellnessLectureReviewId;
    private String content;
}
