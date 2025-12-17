package com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto;

import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.reservation.application.dto.ReservationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WellnessLectureReviewMobileDto {
    private WellnessLectureReviewDto wellnessLectureReviewDto;
    private MemberDtoExtension memberDtoExtension;

    @Builder
    @Getter
    public static class MemberDtoExtension {
        MemberDto memberDto;
        List<WellnessLectureReviewDto> wellnessLectureReviewDtoList;
        List<ReservationDto> reservationDtoList;
    }
}
