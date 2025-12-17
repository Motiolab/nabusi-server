package com.positivehotel.nabusi_server.notificationPackage.notificationSms.application;

import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.NotificationDto;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;

public interface NotificationSmsService {
    SendSmsResponse sendSms(NotificationDto notificationDto);
}
