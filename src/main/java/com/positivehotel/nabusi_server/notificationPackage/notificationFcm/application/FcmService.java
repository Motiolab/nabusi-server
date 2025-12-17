package com.positivehotel.nabusi_server.notificationPackage.notificationFcm.application;

public interface FcmService {
    void send(String token, String title, String body);
}
