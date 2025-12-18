package com.motiolab.nabusi_server.paymentPackage.payment.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatusEnum {
    PAID("결제 완료"),
    UNPAID("미결제"),
    PARTIALLY_PAID("미수금 존재"),
    CANCELE("취소"),
    ;

    private final String description;
}