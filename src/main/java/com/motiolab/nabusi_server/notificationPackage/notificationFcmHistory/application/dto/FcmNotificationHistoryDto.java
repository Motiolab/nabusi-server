package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.dto;

import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.domain.FcmNotificationHistoryEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Builder
public class FcmNotificationHistoryDto {
    private Long id;
    private Long memberId;
    private String title;
    private String body;
    private String detail;
    private ZonedDateTime createdDate;

    public static FcmNotificationHistoryDto from(FcmNotificationHistoryEntity fcmNotificationHistoryEntity) {
        return FcmNotificationHistoryDto.builder()
                .id(fcmNotificationHistoryEntity.getId())
                .memberId(fcmNotificationHistoryEntity.getMemberId())
                .title(fcmNotificationHistoryEntity.getTitle())
                .body(fcmNotificationHistoryEntity.getBody())
                .detail(fcmNotificationHistoryEntity.getDetail())
                .createdDate(fcmNotificationHistoryEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
