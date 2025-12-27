package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewMobileDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.CreateWellnessLectureReviewMobileRequest;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.request.UpdateWellnessLectureReviewMobileRequest;

import java.util.List;

public interface WellnessLectureReviewMobileService {
    void createWellnessLectureReview(Long memberId,
            CreateWellnessLectureReviewMobileRequest createWellnessLectureReviewMobileRequest);

    WellnessLectureReviewMobileDto getWellnessLectureReviewById(Long wellnessLectureReviewId);

    void updateWellnessLectureReview(Long memberId,
            UpdateWellnessLectureReviewMobileRequest updateWellnessLectureReviewMobileRequest);

    List<WellnessLectureReviewMobileDto> getWellnessLectureReviewListByTypeAndId(Long memberId, String type, Long id);
}
