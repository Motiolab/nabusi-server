package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application;

import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.FcmNotificationHistoryDto;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.response.GetNotificationMobileResponse;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.domain.FcmNotificationHistoryEntity;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.domain.FcmNotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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
