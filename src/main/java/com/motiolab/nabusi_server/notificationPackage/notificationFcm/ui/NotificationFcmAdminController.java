package com.motiolab.nabusi_server.notificationPackage.notificationFcm.ui;

import com.motiolab.nabusi_server.notificationPackage.notificationFcm.application.NotificationFcmAdminService;
import com.motiolab.nabusi_server.notificationPackage.notificationFcm.application.dto.request.SendNotificationFcmTestRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NotificationFcmAdminController {
    private final NotificationFcmAdminService notificationFcmAdminService;

    @PostMapping("/v1/admin/notification-fcm/test")
    public ResponseEntity<Boolean> sendNotificationFcmTest(@RequestBody SendNotificationFcmTestRequestV1 sendNotificationFcmTestRequestV1) {
        notificationFcmAdminService.sendNotificationFcmTest(sendNotificationFcmTestRequestV1.getFcmToken(), sendNotificationFcmTestRequestV1.getTitle(), sendNotificationFcmTestRequestV1.getBody());
        return ResponseEntity.ok(true);
    }

}
