package com.motiolab.nabusi_server.centerPackage.center.application;

import com.motiolab.nabusi_server.centerPackage.center.application.dto.CenterAdminDto;
import com.motiolab.nabusi_server.centerPackage.center.application.dto.request.CreateCenterRequestV1;

import java.util.List;

public interface CenterAdminService {
    List<CenterAdminDto> getMyCenterList(Long memberId);
    Boolean createCenter(Long memberId, CreateCenterRequestV1 createCenterRequestV1);
    CenterAdminDto getCenterInfo(Long centerId);
    CenterAdminDto updateCenterInfo(CenterAdminDto centerAdminDto);
}
