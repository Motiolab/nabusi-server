package com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.WellnessLectureReviewCommentDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.domain.WellnessLectureReviewCommentEntity;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.domain.WellnessLectureReviewCommentRepository;
import com.motiolab.nabusi_server.exception.customException.NoAuthorityException;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WellnessLectureReviewCommentServiceImpl implements WellnessLectureReviewCommentService {
    private final WellnessLectureReviewCommentRepository wellnessLectureReviewCommentRepository;

    @Override
    @Transactional
    public WellnessLectureReviewCommentDto create(Long wellnessLectureReviewId, Long memberId, String content) {
        final WellnessLectureReviewCommentEntity entity = WellnessLectureReviewCommentEntity.create(
                wellnessLectureReviewId,
                memberId, content);
        wellnessLectureReviewCommentRepository.save(entity);
        return WellnessLectureReviewCommentDto.from(entity);
    }

    @Override
    public List<WellnessLectureReviewCommentDto> getAllByWellnessLectureReviewId(Long wellnessLectureReviewId) {
        final List<WellnessLectureReviewCommentEntity> wellnessLectureReviewCommentEntityList = wellnessLectureReviewCommentRepository
                .findAllByWellnessLectureReviewId(wellnessLectureReviewId);
        return wellnessLectureReviewCommentEntityList.stream().map(WellnessLectureReviewCommentDto::from).toList();
    }

    @Override
    @Transactional
    public void delete(Long commentId, Long memberId) {
        final WellnessLectureReviewCommentEntity entity = wellnessLectureReviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(WellnessLectureReviewCommentEntity.class, commentId));

        if (!java.util.Objects.equals(entity.getMemberId(), memberId)) {
            throw new NoAuthorityException(WellnessLectureReviewCommentEntity.class, commentId);
        }

        entity.setIsDelete(true);
        wellnessLectureReviewCommentRepository.save(entity);
    }
}
