package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetReviewCommentAuthorityMobileResponse {
    private Long memberId;
    private Boolean isTeacher;
}
