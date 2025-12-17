package com.positivehotel.nabusi_server.centerPackage.centerNotice.application;

import com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto.CenterNoticeDto;

import java.util.List;

public interface CenterNoticeService {
    void create(CenterNoticeDto centerNoticeDto);
    List<CenterNoticeDto> getAllByCenterId(Long centerId);
    CenterNoticeDto getById(Long id);
    void update(CenterNoticeDto centerNoticeDto);
}
