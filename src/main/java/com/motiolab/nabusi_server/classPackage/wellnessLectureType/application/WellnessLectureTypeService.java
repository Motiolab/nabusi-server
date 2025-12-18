package com.motiolab.nabusi_server.classPackage.wellnessLectureType.application;

import com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto.WellnessLectureTypeDto;

import java.util.List;

public interface WellnessLectureTypeService {
    List<WellnessLectureTypeDto> getAllByCenterId(Long centerId);
    WellnessLectureTypeDto getByCenterIdAndName(Long centerId, String name);
    void create(WellnessLectureTypeDto wellnessLectureTypeDto);
    List<WellnessLectureTypeDto> getAllByIdList(List<Long> idList);
    WellnessLectureTypeDto getById(Long id);
}
