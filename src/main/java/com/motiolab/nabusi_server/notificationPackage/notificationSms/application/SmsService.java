package com.motiolab.nabusi_server.notificationPackage.notificationSms.application;

import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsBodyRequest;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;

public interface SmsService {
    SendSmsResponse send(SendSmsBodyRequest sendSmsBodyRequest);
}
