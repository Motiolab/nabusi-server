package com.motiolab.nabusi_server.ticketPackage.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethodEnum {
    IN_APP("인앱 결제"),
    ON_SITE("현장 결제");

    private final String description;
}