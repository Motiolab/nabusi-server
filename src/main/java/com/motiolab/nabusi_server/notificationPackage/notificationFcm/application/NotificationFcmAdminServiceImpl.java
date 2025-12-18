package com.motiolab.nabusi_server.notificationPackage.notificationFcm.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFcmAdminServiceImpl implements NotificationFcmAdminService{
    private final FcmService fcmService;

    @Override
    public void sendNotificationFcmTest(String token, String title, String body) {
        fcmService.send(token, title, body);
    }
}
