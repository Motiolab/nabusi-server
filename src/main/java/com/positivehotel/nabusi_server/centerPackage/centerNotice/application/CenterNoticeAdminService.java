package com.positivehotel.nabusi_server.centerPackage.centerNotice.application;

import com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto.CenterNoticeAdminDto;
import com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto.request.CreateCenterNoticeByCenterIdAdminRequestV1;
import com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto.request.UpdateCenterNoticeByIdAdminRequestV1;

import java.util.List;

public interface CenterNoticeAdminService {
    void createCenterNoticeByCenterId(CreateCenterNoticeByCenterIdAdminRequestV1 createCenterNoticeByCenterIdAdminRequestV1);
    List<CenterNoticeAdminDto> getCenterNoticeListByCenterId(Long centerId);
    CenterNoticeAdminDto getCenterNoticeDetailById(Long id);
    void updateCenterNoticeById(UpdateCenterNoticeByIdAdminRequestV1 updateCenterNoticeByIdAdminRequestV1);
}
