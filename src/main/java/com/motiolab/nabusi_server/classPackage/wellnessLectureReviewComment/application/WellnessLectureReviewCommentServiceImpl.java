package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.WellnessLectureReviewCommentDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.domain.WellnessLectureReviewCommentEntity;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.domain.WellnessLectureReviewCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WellnessLectureReviewCommentServiceImpl implements WellnessLectureReviewCommentService {
    private final WellnessLectureReviewCommentRepository wellnessLectureReviewCommentRepository;

    @Override
    @Transactional
    public WellnessLectureReviewCommentDto create(Long wellnessLectureReviewId, Long memberId, String content) {
        final WellnessLectureReviewCommentEntity entity = WellnessLectureReviewCommentEntity.create(wellnessLectureReviewId,
                memberId, content);
        wellnessLectureReviewCommentRepository.save(entity);
        return WellnessLectureReviewCommentDto.from(entity);
    }
}
