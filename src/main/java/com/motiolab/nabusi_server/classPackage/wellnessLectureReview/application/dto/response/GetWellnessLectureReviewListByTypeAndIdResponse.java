package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetWellnessLectureReviewListByTypeAndIdResponse {
    private Double avrageRating;
    private Integer fiveRatingCnt;
    private Integer fourRatingCnt;
    private Integer threeRatingCnt;
    private Integer twoRatingCnt;
    private Integer oneRatingCnt;
    private List<WellnessLectureReviewResponse> wellnessLectureReviewResponseList;

    @Builder
    @Getter
    public static class WellnessLectureReviewResponse {
        private Long id;
        private String content;
        private Integer rating;
        private Long memberId;
        private Integer memberCheckInCnt;
        private Integer memberReviewCnt;
        private Boolean isCreateCommentAvailable;
    }
}
