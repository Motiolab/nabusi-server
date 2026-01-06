package com.motiolab.nabusi_server.classPackage.wellnessClass.application;

import com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.WellnessClassDto;

import java.util.List;

public interface WellnessClassService {
    WellnessClassDto getByCenterIdAndName(Long centerId, String name);

    void create(WellnessClassDto wellnessClassDto);

    WellnessClassDto getByIdAndCenterId(Long id, Long centerId);

    Boolean update(WellnessClassDto wellnessClassDto);

    List<WellnessClassDto> getAllById(List<Long> idList);

    List<WellnessClassDto> getAllByWellnessTicketManagementIdListIn(List<Long> wellnessTicketManagementIdList);

    WellnessClassDto getById(Long id);

    List<Long> getDistinctTeacherIds();

    List<WellnessClassDto> getAllByCenterIdAndIsDeleteFalse(Long centerId);
}
