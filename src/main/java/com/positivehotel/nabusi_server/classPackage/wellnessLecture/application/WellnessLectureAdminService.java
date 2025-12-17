package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application;

import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureAdminDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.request.CreateWellnessLectureListWithWellnessClassAdminRequestV1;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.request.UpdateWellnessLectureAdminRequestV1;

import java.time.ZonedDateTime;
import java.util.List;

public interface WellnessLectureAdminService {
    void createWellnessLectureListWithWellnessClass(CreateWellnessLectureListWithWellnessClassAdminRequestV1 createWellnessLectureListWithWellnessClassAdminRequestV1);
    List<WellnessLectureAdminDto> getWellnessLectureListByStartDate(ZonedDateTime startDateTime);
    WellnessLectureAdminDto getWellnessLectureDetailById(Long id);
    void deleteWellnessLectureById(Long id, Boolean isSendNoti);
    void restoreWellnessLectureById(Long id);
    void updateWellnessLecture(UpdateWellnessLectureAdminRequestV1 updateWellnessLectureAdminRequestV1);
}
