package com.positivehotel.nabusi_server.fcmTokenMobile.application;

public interface FcmTokenMobileService {
    void createOrUpdateFcmTokenMobile(String token, Long memberId);
    void updateIsLogoutByMobile(Long memberId);
}
