package com.motiolab.nabusi_server.paymentPackage.payment.application;

import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CancelTossPayRequest;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CreateTossPayRequest;

public interface PaymentMobileService {
    PaymentDto createReservationWithTossPay(CreateTossPayRequest tossPayRequest);
    void cancelTossPay(CancelTossPayRequest tosPayCancelRequest);
}
