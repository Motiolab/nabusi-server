package com.motiolab.nabusi_server.paymentPackage.payment.application;

import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CancelTossPayRequest;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CreateTossPayRequest;

public interface PaymentMobileService {
    void createReservationWithTossPay(CreateTossPayRequest tossPayRequest);
    void cancelTossPay(CancelTossPayRequest tosPayCancelRequest);
}
