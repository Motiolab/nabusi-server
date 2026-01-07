package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassAdminDto;

import java.util.List;

public interface WellnessClassAdminService {
    List<WellnessClassAdminDto> getWellnessClassNameListByCenterId(Long centerId);

    void createWellnessClassByCenterIdAndName(Long registerId, Long centerId, String wellnessClassName);

    WellnessClassAdminDto getByIdAndCenterId(Long id, Long centerId);

    List<WellnessClassAdminDto> getWellnessClassAllByCenterId(Long centerId);

    List<WellnessClassAdminDto> getWellnessClassDetailWithLectureByCenterId(Long centerId);
    void deleteWellnessClassById(Long id, Long centerId);
}
