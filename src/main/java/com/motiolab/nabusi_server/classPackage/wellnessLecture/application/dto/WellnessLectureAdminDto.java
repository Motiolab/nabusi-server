package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;
import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.WellnessTicketDto;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.application.dto.WellnessTicketManagementDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WellnessLectureAdminDto {
    private WellnessLectureDto wellnessLectureDto;
    private TeacherDto teacherDto;
    private WellnessLectureTypeDto wellnessLectureTypeDto;
    private List<WellnessTicketExtension> wellnessTicketExtensionList;

    @Builder
    @Getter
    public static class WellnessTicketExtension {
        WellnessTicketManagementDto wellnessTicketManagementDto;
        WellnessTicketDto wellnessTicketDto;
    }
}
