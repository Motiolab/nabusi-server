package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassMobileDto;

import java.util.List;

public interface WellnessClassMobileService {
    List<WellnessClassMobileDto> getAllWellnessClassListByCenterId(Long centerId);
}
