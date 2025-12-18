package com.motiolab.nabusi_server.mobileAuthCode.application;

import com.motiolab.nabusi_server.mobileAuthCode.application.dto.request.SendMobileAuthCodeRequestV1;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;

public interface MobileAuthCodeService {
    SendSmsResponse sendMobileAuthCode(SendMobileAuthCodeRequestV1 sendMobileAuthCodeRequestV1);
    Boolean verificationMobileAuthCode(String authCode);
}
