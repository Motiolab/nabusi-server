package com.positivehotel.nabusi_server.classPackage.wellnessClass.application;

import com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassAdminDto;

import java.util.List;

public interface WellnessClassAdminService {
    List<WellnessClassAdminDto> getWellnessClassNameListByCenterId(Long centerId);
    void createWellnessClassByCenterIdAndName(Long registerId, Long centerId, String wellnessClassName);
    WellnessClassAdminDto getByIdAndCenterId(Long id, Long centerId);
}
