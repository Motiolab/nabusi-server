package com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application;

import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeAdminDto;
import com.positivehotel.nabusi_server.classPackage.wellnessLectureType.application.dto.request.CreateWellnessLectureTypeByCenterIdAdminRequestV1;

import java.util.List;

public interface WellnessLectureTypeAdminService {
    List<WellnessLectureTypeAdminDto> getWellnessLectureTypeNameListByCenterId(Long centerId);
    void createWellnessLectureTypeByCenterId(Long centerId, CreateWellnessLectureTypeByCenterIdAdminRequestV1 createWellnessLectureTypeByCenterIdAdminRequestV1);
}
