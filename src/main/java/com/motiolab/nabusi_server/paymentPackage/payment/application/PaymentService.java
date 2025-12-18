package com.motiolab.nabusi_server.paymentPackage.payment.application;

import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;

public interface PaymentService {
    PaymentDto create(PaymentDto paymentDto);
    PaymentDto update(PaymentDto paymentDto);
    PaymentDto getByTossPayId(Long tossPayId);
}
