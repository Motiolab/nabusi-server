package com.positivehotel.nabusi_server.notificationPackage.notificationSms.application;

import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsBodyRequest;
import com.positivehotel.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService{
    @Value("${naver-cloud.domain}")
    private String domain;
    @Value("${naver-cloud.sms.serviceId}")
    private String serviceId;
    @Value("${naver-cloud.secretKey}")
    private String secretKey;
    @Value("${naver-cloud.accessKey}")
    private String accessKey;

    @Override
    public SendSmsResponse send(SendSmsBodyRequest sendSmsBodyRequest) {
        final Long time = System.currentTimeMillis();
        final String signature = generateSignature(time);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", signature);

        final HttpEntity<?> request = new HttpEntity<>(sendSmsBodyRequest, headers);

        final String url = domain + "/sms/v2/services/" + serviceId + "/messages";

        final RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(url, request, SendSmsResponse.class);
    }

    private String generateSignature(Long time) {
        String space = " ";                    // 공백
        String newLine = "\n";                 // 줄바꿈
        String postMethod = "POST";                // HTTP 메소드
        String url = "/sms/v2/services/" + serviceId + "/messages";  // 도메인을 제외한 "/"
        String timestamp = time.toString();

        String message = postMethod + space + url + newLine + timestamp + newLine + accessKey;

        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }
}
