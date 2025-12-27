package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application;

import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.dto.TossPayDto;

public interface TossPayService {
    TossPayDto create(TossPayDto tossPayDto);

    TossPayDto getByPaymentKey(String paymentKey);

    void update(TossPayDto tossPayDto);

    TossPayDto getById(Long id);
}
