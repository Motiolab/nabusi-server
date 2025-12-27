package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WellnessLectureReviewCommentRepository
        extends JpaRepository<WellnessLectureReviewCommentEntity, Long> {
    List<WellnessLectureReviewCommentEntity> findAllByWellnessLectureReviewId(Long wellnessLectureReviewId);
}
