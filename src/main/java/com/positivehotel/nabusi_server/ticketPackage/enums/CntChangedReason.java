package com.positivehotel.nabusi_server.ticketPackage.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CntChangedReason {
    MEMBER_RESERVATION("회원 예약", -1),
    MEMBER_CANCELED_RESERVATION("회원  예약취소", 1),
    MEMBER_ISSUED("회원 발행", 0),
    ADMIN_RESERVATION("관리자 예약", -1),
    ADMIN_CANCELED_RESERVATION("관리자  예약취소", 1),
    ADMIN_MODIFY_CNT("관리자 횟수 수정", 0),
    ADMIN_ISSUED("관리자 발행", 0),
    ADMIN_DELETE_RESERVATION("관리자 예약삭제", 1)
    ;
    private final String description;
    private final Integer changedCnt;
}
