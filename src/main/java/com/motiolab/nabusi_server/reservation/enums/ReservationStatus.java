package com.motiolab.nabusi_server.reservation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    INAPP_RESERVATION("인앱 예약"),
    INAPP_PAYMENT_RESERVATION("인앱 결제 예약"),
    ADMIN_RESERVATION("관리자 예약"),
    ONSITE_RESERVATION("현장 예약"),
    MEMBER_CANCELED_RESERVATION("회원 예약 취소"),
    ADMIN_CANCELED_RESERVATION("관리자 예약 취소"),
    CHECK_IN("출석"),
    ABSENT("결석"),
    ;
    private final String description;
}
