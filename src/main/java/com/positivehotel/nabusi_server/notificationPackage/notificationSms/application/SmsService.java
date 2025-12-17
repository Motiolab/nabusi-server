package com.positivehotel.nabusi_server.notificationPackage.notificationSms.application;

import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsBodyRequest;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;

public interface SmsService {
    SendSmsResponse send(SendSmsBodyRequest sendSmsBodyRequest);
}
