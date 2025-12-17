package com.positivehotel.nabusi_server.fcmTokenMobile.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.fcmTokenMobile.application.FcmTokenMobileService;
import com.positivehotel.nabusi_server.fcmTokenMobile.application.dto.request.CreateFcmTokenMobileRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FcmTokenMobileController {
    private final FcmTokenMobileService fcmTokenMobileService;

    @PostMapping("/v1/mobile/fcm-token-mobile")
    public ResponseEntity<String> createFcmTokenMobile(@MemberId Long memberId, @RequestBody CreateFcmTokenMobileRequestV1 createFcmTokenMobileRequestV1) throws JsonProcessingException {
        fcmTokenMobileService.createOrUpdateFcmTokenMobile(createFcmTokenMobileRequestV1.getFcmToken(), memberId);
        return ResponseEntity.ok(createFcmTokenMobileRequestV1.getFcmToken());
    }

    @PutMapping("/v1/mobile/fcm-token-mobile")
    public ResponseEntity<Boolean> logoutFcmTokenMobile(@MemberId Long memberId) {
        fcmTokenMobileService.updateIsLogoutByMobile(memberId);
        return ResponseEntity.ok(true);
    }
}
