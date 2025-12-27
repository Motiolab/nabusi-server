package com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessLectureReviewComment.application.dto.WellnessLectureReviewCommentDto;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WellnessLectureReviewMobileDto {
    private WellnessLectureReviewDto wellnessLectureReviewDto;
    private Boolean isCreateCommentAvailable;
    private MemberDtoExtension memberDtoExtension;
    private List<WellnessLectureReviewCommentDto> wellnessLectureReviewCommentDtoList;

    @Builder
    @Getter
    public static class MemberDtoExtension {
        MemberDto memberDto;
        List<WellnessLectureReviewDto> wellnessLectureReviewDtoList;
        List<ReservationDto> reservationDtoList;
    }
}
