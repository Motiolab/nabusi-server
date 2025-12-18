package com.motiolab.nabusi_server.notificationPackage.notificationSms.application;

import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.NotificationDto;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;

public interface NotificationSmsService {
    SendSmsResponse sendSms(NotificationDto notificationDto);
}
