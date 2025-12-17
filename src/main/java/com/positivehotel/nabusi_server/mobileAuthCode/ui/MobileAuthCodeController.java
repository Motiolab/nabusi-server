package com.positivehotel.nabusi_server.mobileAuthCode.ui;

import com.positivehotel.nabusi_server.mobileAuthCode.application.MobileAuthCodeService;
import com.positivehotel.nabusi_server.mobileAuthCode.application.dto.request.SendMobileAuthCodeRequestV1;
import com.positivehotel.nabusi_server.mobileAuthCode.application.dto.response.VerificationMobileAuthCodeResponseV1;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MobileAuthCodeController {
    private final MobileAuthCodeService mobileAuthCodeService;

    @PostMapping("/v1/mobile/auth/code")
    public ResponseEntity<SendSmsResponse> sendMobileAuthCode(@RequestBody SendMobileAuthCodeRequestV1 sendMobileAuthCodeRequestV1) {
        final SendSmsResponse sendSmSResponse = mobileAuthCodeService.sendMobileAuthCode(sendMobileAuthCodeRequestV1);
        return ResponseEntity.ok(sendSmSResponse);
    }

    @GetMapping("/v1/verification/mobile/auth/code/{authCode}")
    public ResponseEntity<VerificationMobileAuthCodeResponseV1> verificationMobileAuthCode(final @PathVariable String authCode) {
        final Boolean isAuthChecked = mobileAuthCodeService.verificationMobileAuthCode(authCode);
        return ResponseEntity.ok(VerificationMobileAuthCodeResponseV1.builder().success(isAuthChecked).build());
    }
}
