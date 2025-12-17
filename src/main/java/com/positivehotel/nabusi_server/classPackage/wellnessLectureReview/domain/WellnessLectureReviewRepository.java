package com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WellnessLectureReviewRepository extends JpaRepository<WellnessLectureReviewEntity, Long> {
     List<WellnessLectureReviewEntity> findAllByWellnessLectureIdIn(List<Long> wellnessLectureIdList);
     List<WellnessLectureReviewEntity> findAllByWellnessLectureId(Long wellnessLectureId);
     List<WellnessLectureReviewEntity> findAllByWellnessClassIdIn(List<Long> wellnessClassIdList);
     List<WellnessLectureReviewEntity> findAllByTeacherIdIn(List<Long> teacherIdList);
     List<WellnessLectureReviewEntity> findAllByMemberIdAndWellnessLectureIdIn(Long memberId, List<Long> wellnessLectureIdList);
     Optional<WellnessLectureReviewEntity> findByMemberIdAndWellnessLectureId(Long memberId, Long wellnessLectureId);
     List<WellnessLectureReviewEntity> findAllByWellnessClassId(Long wellnessClassId);
     List<WellnessLectureReviewEntity> findAllByMemberIdIn(List<Long> memberIdList);
     List<WellnessLectureReviewEntity> findAllByCenterId(Long centerId);
     List<WellnessLectureReviewEntity> findAllByTeacherId(Long teacherId);
     List<WellnessLectureReviewEntity> findAllByCenterIdIn(List<Long> centerIdList);
}
