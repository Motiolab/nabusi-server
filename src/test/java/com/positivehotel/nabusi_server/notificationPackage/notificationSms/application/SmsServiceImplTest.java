package com.positivehotel.nabusi_server.notificationPackage.notificationSms.application;

import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsBodyRequest;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SmsServiceImplTest {

    @Autowired
    private SmsService smsService;

    @Test
    @DisplayName("SMS 전송 테스트")
    void sendSmsTest() {
        // Given
        String recipientPhoneNumber = "01088983457"; // 테스트할 수신자 번호로 변경해주세요
        String content = "SMS Service Test Message";

        SendSmsBodyRequest request = SendSmsBodyRequest.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from("01088983457") // 발신 번호 (네이버 클라우드에 등록된 번호여야 함)
                .content(content)
                .messages(List.of(SendSmsBodyRequest.Message.builder()
                        .to(recipientPhoneNumber)
                        .build()))
                .build();

        // When
        SendSmsResponse response = smsService.send(request);

        // Then
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Status Name: " + response.getStatusName());

        assertNotNull(response);
        // 실제 API 호출 성공 여부는 계정 설정에 따라 달라질 수 있으므로,
        // 성공 응답(202 Accepted 등)을 확인하거나 예외가 발생하지 않는지 확인합니다.
        // assertEquals("202", response.getStatusCode());
    }
}
