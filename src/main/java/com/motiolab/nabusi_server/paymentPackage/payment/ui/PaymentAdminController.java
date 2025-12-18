package com.motiolab.nabusi_server.paymentPackage.payment.ui;


import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.paymentPackage.payment.application.PaymentAdminService;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CreateOnSitePayUnpaidAdminRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentAdminController {
    private final PaymentAdminService paymentAdminService;

    @PostMapping("/v1/admin/wellness-ticket-payment/unpaid/{centerId}")
    public ResponseEntity<Boolean> createOnSitePayUnpaid(@MemberId Long memberId, @PathVariable Long centerId, @RequestBody CreateOnSitePayUnpaidAdminRequestV1 createOnSitePayUnpaidAdminRequestV1) {
        createOnSitePayUnpaidAdminRequestV1.setActionMemberId(memberId);
        createOnSitePayUnpaidAdminRequestV1.setPayeeMemberId(memberId);
        paymentAdminService.createOnSitePayUnpaid(createOnSitePayUnpaidAdminRequestV1);
        return ResponseEntity.ok(true);
    }
}
