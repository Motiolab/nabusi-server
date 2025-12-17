package com.positivehotel.nabusi_server.paymentPackage.payment.application;

import com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.request.CancelTossPayRequest;
import com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.request.CreateTossPayRequest;

public interface PaymentMobileService {
    void createReservationWithTossPay(CreateTossPayRequest tossPayRequest);
    void cancelTossPay(CancelTossPayRequest tosPayCancelRequest);
}
