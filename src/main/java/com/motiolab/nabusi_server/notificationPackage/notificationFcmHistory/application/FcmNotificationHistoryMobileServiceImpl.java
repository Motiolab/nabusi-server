package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application;

import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.FcmNotificationHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmNotificationHistoryMobileServiceImpl implements FcmNotificationHistoryMobileService {
    private final FcmNotificationHistoryService fcmNotificationHistoryService;

    @Override
    public List<FcmNotificationHistoryDto> getAllByMemberId(Long memberId) {
        return fcmNotificationHistoryService.getAllByMemberId(memberId);
    }
}
