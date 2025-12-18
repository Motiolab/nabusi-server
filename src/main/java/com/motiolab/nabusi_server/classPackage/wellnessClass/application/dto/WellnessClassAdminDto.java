package com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto;

import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WellnessClassAdminDto {
    private WellnessClassDto wellnessClassDto;
    private TeacherDto teacherDto;
}
