package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application;

import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.FcmNotificationHistoryDto;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.response.GetNotificationMobileResponse;

import java.util.List;

public interface FcmNotificationHistoryMobileService {
    List<FcmNotificationHistoryDto> getAllByMemberId(Long memberId);
}
