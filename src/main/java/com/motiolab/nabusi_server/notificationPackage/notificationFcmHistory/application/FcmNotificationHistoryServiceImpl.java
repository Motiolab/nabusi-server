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
public class FcmNotificationHistoryServiceImpl implements FcmNotificationHistoryService {
    private final FcmNotificationHistoryRepository fcmNotificationHistoryRepository;

    @Override
    public void save(Long memberId, String title, String body, String detail) {
        final FcmNotificationHistoryEntity fcmNotificationHistoryEntity = FcmNotificationHistoryEntity.create(
                memberId, title, body, detail);
        fcmNotificationHistoryRepository.save(fcmNotificationHistoryEntity);
    }

    @Override
    public List<FcmNotificationHistoryDto> getAllByMemberId(Long memberId) {
        final List<FcmNotificationHistoryEntity> entityList = fcmNotificationHistoryRepository
                .findAllByMemberIdOrderByCreatedDateDesc(memberId);
        return entityList.stream().map(FcmNotificationHistoryDto::from).toList();
    }
}
