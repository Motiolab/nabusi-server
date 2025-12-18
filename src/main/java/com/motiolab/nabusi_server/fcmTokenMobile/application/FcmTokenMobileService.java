package com.motiolab.nabusi_server.fcmTokenMobile.application;

public interface FcmTokenMobileService {
    void createOrUpdateFcmTokenMobile(String token, Long memberId);
    void updateIsLogoutByMobile(Long memberId);
}
