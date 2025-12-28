package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.WellnessLectureReviewCommentDto;

import java.util.List;

public interface WellnessLectureReviewCommentService {
    WellnessLectureReviewCommentDto create(Long wellnessLectureReviewId, Long memberId, String content);

    List<WellnessLectureReviewCommentDto> getAllByWellnessLectureReviewId(Long wellnessLectureReviewId);

    void delete(Long commentId, Long memberId);
}
