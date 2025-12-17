package com.positivehotel.nabusi_server.centerPackage.center.application;

import com.positivehotel.nabusi_server.centerPackage.center.application.dto.CenterDto;

import java.util.List;

public interface CenterService {
    List<CenterDto> getAllByMemberId(Long memberId);
    List<CenterDto> getAllByIsActiveTrue();
    CenterDto postCenter(CenterDto centerDto);
    CenterDto getById(Long id);
    CenterDto put(CenterDto centerDto);
    List<CenterDto> getAllByIdList(List<Long> idList);
}
