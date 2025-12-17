package com.positivehotel.nabusi_server.centerPackage.center.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterOpenInfoDto;

import java.util.List;

public interface CenterOpenInfoService {
    List<CenterOpenInfoDto> getAllByCenterId(Long centerId);
    void deleteByCenterId(Long centerId);
    Boolean saveAll(List<CenterOpenInfoDto> centerOpenInfoDtoList);
}
