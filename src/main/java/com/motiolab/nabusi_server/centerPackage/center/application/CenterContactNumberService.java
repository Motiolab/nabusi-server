package com.motiolab.nabusi_server.centerPackage.center.application;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterContactNumberDto;

import java.util.List;

public interface CenterContactNumberService {
    List<CenterContactNumberDto> getAllByCenterId(Long centerId);
    void deleteByCenterId(Long centerId);
    Boolean saveAll(List<CenterContactNumberDto> centerContactNumberDtoList);
}
