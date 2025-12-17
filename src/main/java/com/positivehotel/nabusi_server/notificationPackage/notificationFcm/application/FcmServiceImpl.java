package com.positivehotel.nabusi_server.notificationPackage.notificationFcm.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService{

    @Override
    public void send(String token, String title, String body) {
        try {
            // 푸시 알림 생성
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            // FCM을 이용하여 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("🔥 Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
