package com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WellnessClassAdminDto {
    private WellnessClassDto wellnessClassDto;
    private TeacherDto teacherDto;
    private WellnessLectureTypeDto wellnessLectureTypeDto;
    private List<WellnessTicketDto> wellnessTicketDtoList;
    private List<WellnessLectureExtension> wellnessLectureExtensionList;

    @Builder
    @Getter
    public static class WellnessLectureExtension {
        private WellnessLectureDto wellnessLectureDto;
        private List<ReservationDto> reservationDtoList;
    }
}
