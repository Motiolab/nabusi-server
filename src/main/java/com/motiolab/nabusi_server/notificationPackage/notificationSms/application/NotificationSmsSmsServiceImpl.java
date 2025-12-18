package com.motiolab.nabusi_server.notificationPackage.notificationSms.application;

import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.NotificationDto;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsBodyRequest;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationSmsSmsServiceImpl implements NotificationSmsService {
    private final SmsService smsService;

    @Override
    public SendSmsResponse sendSms(NotificationDto notificationDto) {
        List<SendSmsBodyRequest.Message> messages = new ArrayList<>();
        final String content = "[" + notificationDto.getCenterName() + "] 관리자 초대장" +
                "롤 : "+ notificationDto.getRoleName() +
                "초대 링크" +
                "http://localhost:3000/invite/admin?code=" + notificationDto.getCode();
        final String subject = "센터 관리자 초대장";

        messages.add(SendSmsBodyRequest.Message.builder()
                .to(notificationDto.getMobile())
                .content(content)
                .subject(subject)
                .build());

        SendSmsBodyRequest sendSmsBodyRequest = SendSmsBodyRequest.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode(notificationDto.getCountryCode())
                .from("0205457633")
                .content(content)
                .messages(messages)
                .build();

        return smsService.send(sendSmsBodyRequest);
    }
}
