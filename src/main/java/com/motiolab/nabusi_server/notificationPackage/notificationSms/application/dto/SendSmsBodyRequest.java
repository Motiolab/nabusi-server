package com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SendSmsBodyRequest {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<Message> messages;

    @Builder
    @Getter
    public static class Message {
        private String to;
        private String content;
        private String subject;
    }
}
