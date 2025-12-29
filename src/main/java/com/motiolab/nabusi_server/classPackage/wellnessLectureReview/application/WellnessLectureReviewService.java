package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;

import java.util.List;

public interface WellnessLectureReviewService {
    List<WellnessLectureReviewDto> getAllByWellnessLectureIdList(List<Long> wellnessLectureIdList);

    List<WellnessLectureReviewDto> getAllByWellnessLectureId(Long wellnessLectureId);

    List<WellnessLectureReviewDto> getAllByWellnessClassIdList(List<Long> wellnessClassIdList);

    List<WellnessLectureReviewDto> getAllByTeacherIdList(List<Long> teacherIdList);

    List<WellnessLectureReviewDto> getAllByMemberIdAndWellnessLectureIdList(Long memberId,
            List<Long> wellnessLectureIdList);

    WellnessLectureReviewDto getByMemberIdAndWellnessLectureId(Long memberId, Long wellnessLectureId);

    WellnessLectureReviewDto create(WellnessLectureReviewDto wellnessLectureReviewDto);

    WellnessLectureReviewDto getById(Long id);

    void update(WellnessLectureReviewDto wellnessLectureReviewDto);

    List<WellnessLectureReviewDto> getAllByWellnessClassId(Long wellnessClassId);

    List<WellnessLectureReviewDto> getAllByMemberIdList(List<Long> memberIdList);

    List<WellnessLectureReviewDto> getAllByCenterId(Long centerId);

    List<WellnessLectureReviewDto> getAllByTeacherId(Long teacherId);

    List<WellnessLectureReviewDto> getAllByCenterIdList(List<Long> centerIdList);

    long countByMemberId(Long memberId);
}
