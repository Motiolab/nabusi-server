package com.positivehotel.nabusi_server.teacher.application;

import com.positivehotel.nabusi_server.teacher.application.dto.TeacherAdminDto;
import com.positivehotel.nabusi_server.teacher.application.dto.request.UpdateTeacherCareerByIdAdminRequestV1;
import com.positivehotel.nabusi_server.teacher.application.dto.request.UpdateTeacherImageUrlByIdAdminRequestV1;
import com.positivehotel.nabusi_server.teacher.application.dto.request.UpdateTeacherIntroduceAndNickNameByIdAdminRequestV1;

import java.util.List;

public interface TeacherAdminService {
    void addTeacherByCenterId(Long centerId, Long memberId);
    List<TeacherAdminDto> getTeacherNameListByCenterId(Long centerId);
    List<TeacherAdminDto> getTeacherListByCenterId(Long centerId);
    TeacherAdminDto getTeacherDetailById(Long id);
    void updateTeacherIntroduceAndNickNameById(UpdateTeacherIntroduceAndNickNameByIdAdminRequestV1 updateTeacherIntroduceAndNickNameByIdAdminRequestV1);
    void updateTeacherCareerById(UpdateTeacherCareerByIdAdminRequestV1 updateTeacherCareerByIdAdminRequestV1);
    void deleteTeacherById(Long id);
    void restoreTeacherById(Long id);
    void updateTeacherImageUrlById(UpdateTeacherImageUrlByIdAdminRequestV1 updateTeacherImageUrlByIdAdminRequestV1);
}
