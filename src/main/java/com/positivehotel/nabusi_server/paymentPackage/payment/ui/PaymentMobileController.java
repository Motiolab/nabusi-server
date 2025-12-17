package com.positivehotel.nabusi_server.paymentPackage.payment.ui;

import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.paymentPackage.payment.application.PaymentMobileService;
import com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.request.CreateTossPayRequest;
import com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.response.CreateTossPayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class PaymentMobileController {
    private final PaymentMobileService paymentMobileService;

    @PostMapping("/v1/mobile/toss-pay/create")
    public ResponseEntity<CreateTossPayResponse> createTossPay(@MemberId Long memberId, @RequestBody CreateTossPayRequest createTossPayRequest) {
        createTossPayRequest.setMemberId(memberId);
        paymentMobileService.createReservationWithTossPay(createTossPayRequest);
        return ResponseEntity.ok(CreateTossPayResponse.builder().success(true).build());
    }
}
