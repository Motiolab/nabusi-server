package com.positivehotel.nabusi_server.teacher.application;

import com.positivehotel.nabusi_server.teacher.application.dto.TeacherMobileDto;

import java.util.List;

public interface TeacherMobileService {
    List<TeacherMobileDto> getTeacherListByCenterId(Long centerId);
}
