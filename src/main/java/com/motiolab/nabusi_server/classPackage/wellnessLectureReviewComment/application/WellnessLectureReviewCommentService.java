package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.WellnessLectureReviewCommentDto;

public interface WellnessLectureReviewCommentService {
    WellnessLectureReviewCommentDto create(Long wellnessLectureReviewId, Long memberId, String content);
}
