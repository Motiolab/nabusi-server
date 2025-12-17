package com.positivehotel.nabusi_server.centerPackage.center.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterRoomDto;

import java.util.List;

public interface CenterRoomService {
    List<CenterRoomDto> getAllByCenterId(Long centerId);
    void deleteByCenterId(Long centerId);
    Boolean saveAll(List<CenterRoomDto> centerRoomDtoList);
    void create(CenterRoomDto centerRoomDto);
    CenterRoomDto getByCenterIdAndName(Long centerId, String name);
    CenterRoomDto getById(Long id);
    List<CenterRoomDto> getAllByIdList(List<Long> idList);
}
