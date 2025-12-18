package com.motiolab.nabusi_server.teacher.application;

import com.motiolab.nabusi_server.teacher.application.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    List<TeacherDto> getAllByIdList(List<Long> teacherIdList);
    TeacherDto getByCenterIdAndMemberId(Long centerId, Long memberId);
    void create(TeacherDto teacherDto);
    List<TeacherDto> getAllByCenterId(Long centerId);
    TeacherDto getById(Long id);
    void update(TeacherDto teacherDto);
    void delete(Long id);
    void restore(Long id);
}
