package com.motiolab.nabusi_server.centerPackage.center.application;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterRoomAdminDto;

import java.util.List;

public interface CenterRoomAdminService {
    List<CenterRoomAdminDto> getCenterRoomListByCenterId(Long centerId);
    void createCenterRoomByCenterId(Long centerId, String centerRoomName);
}
