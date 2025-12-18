package com.motiolab.nabusi_server.teacher.application;

import com.motiolab.nabusi_server.teacher.application.dto.TeacherMobileDto;

import java.util.List;

public interface TeacherMobileService {
    List<TeacherMobileDto> getTeacherListByCenterId(Long centerId);
}
