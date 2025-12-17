package com.positivehotel.nabusi_server.teacher.application.dto;

import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeacherAdminDto {
    TeacherDto teacherDto;
    List<WellnessLectureDto> wellnessLectureDtoList;
}

