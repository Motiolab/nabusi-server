package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.FcmNotificationHistoryService;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.FcmNotificationHistoryDto;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto.response.GetNotificationMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FcmNotificationHistoryMobileController {
    private final FcmNotificationHistoryService fcmNotificationHistoryService;

    @GetMapping("/v1/mobile/fcm-notification-history/list")
    public ResponseEntity<List<GetNotificationMobileResponse>> getAllByMemberId(@MemberId Long memberId) {
        final List<FcmNotificationHistoryDto> fcmNotificationHistoryDtoList = fcmNotificationHistoryService.getAllByMemberId(memberId);
        final List<GetNotificationMobileResponse> getNotificationMobileResponseList = fcmNotificationHistoryDtoList.stream()
                .map(fcmNotificationHistoryDto -> GetNotificationMobileResponse.builder()
                        .id(fcmNotificationHistoryDto.getId())
                        .title(fcmNotificationHistoryDto.getTitle())
                        .body(fcmNotificationHistoryDto.getBody())
                        .detail(fcmNotificationHistoryDto.getDetail())
                        .createdDate(fcmNotificationHistoryDto.getCreatedDate())
                        .build()
                ).toList();
        return ResponseEntity.ok(getNotificationMobileResponseList);
    }
}
