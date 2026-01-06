package com.motiolab.nabusi_server.notificationPackage.notificationFcm.application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    @Override
    public void send(String token, String title, String body) {
        try {
            log.info("📧 Attempting to send FCM message. Title: {}, Token (start): {}",
                    title, token.substring(0, Math.min(token.length(), 10)) + "...");

            // 푸시 알림 생성
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder()
                                    .setAlert(title)
                                    .setSound("default")
                                    .build())
                            .putHeader("apns-topic", "com.motiolab.nabusi-ios")
                            .putHeader("apns-push-type", "alert")
                            .putHeader("apns-priority", "10")
                            .build())
                    .build();

            // FCM을 이용하여 메시지 전송
            FirebaseApp app = FirebaseApp.getInstance();
            log.info("🔗 Using Firebase App: {} (Project ID: {})", app.getName(), app.getOptions().getProjectId());

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("✅ Successfully sent message. Response: {}", response);
        } catch (Exception e) {
            log.error("❌ Failed to send FCM message: {}", e.getMessage(), e);
        }
    }
}
