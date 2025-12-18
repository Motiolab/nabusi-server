package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterContactNumberDto;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureReview.application.dto.WellnessLectureReviewDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WellnessLectureMobileDto {
    private WellnessLectureDto wellnessLectureDto;
    private TeacherDto teacherDto;
    private WellnessLectureTypeDto wellnessLectureTypeDto;
    private ReservationDto myReservationDto;
    private List<ReservationExtension> reservationExtensionList;
    private WellnessLectureReviewDto myWellnessLectureReviewDto;
    private CenterDto centerDto;
    private List<CenterContactNumberDto> centerContactNumberDto;
    private List<WellnessTicketIssuanceDto> myWellnessTicketIssuanceDtoAvaiableList;
    private List<WellnessLectureReviewDto> wellnessLectureReviewDtoList;

    @Builder
    @Getter
    public static class ReservationExtension {
        ReservationDto reservationDto;
        WellnessTicketIssuanceDto wellnessTicketIssuanceDto;
    }
}
