package com.positivehotel.nabusi_server.reservation.application.dto.response;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.positivehotel.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.positivehotel.nabusi_server.reservation.application.dto.ReservationDto;
import com.positivehotel.nabusi_server.teacher.application.dto.TeacherDto;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReservationMobileDto {
    private ReservationDto reservationDto;
    private WellnessLectureDto wellnessLectureDto;
    private WellnessLectureTypeDto wellnessLectureTypeDto;
    private TeacherDto teacherDto;
    private MemberExtension memberExtension;
    private WellnessTicketIssuanceDto wellnessTicketIssuanceDto;
    private CenterDto centerDto;
    private WellnessLectureReviewDto myWellnessLectureReviewDto;
    private List<WellnessLectureReviewDto> wellnessLectureReviewDtoList;

    @Builder
    @Getter
    public static class MemberExtension {
        private MemberDto memberDto;
        private MemberMemoDto memberMemoDto;
    }
}
