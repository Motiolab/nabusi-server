package com.positivehotel.nabusi_server.urlPattern.application;

import com.positivehotel.nabusi_server.urlPattern.application.dto.UrlPatternDto;

import java.util.List;

public interface UrlPatternService {
    List<UrlPatternDto> postAll(List<UrlPatternDto> urlPatternDtoList);
    List<UrlPatternDto> getAllByCenterIdAndActionName(Long centerId, String actionName);
    List<UrlPatternDto> getAllByCenterId(Long centerId);
}
