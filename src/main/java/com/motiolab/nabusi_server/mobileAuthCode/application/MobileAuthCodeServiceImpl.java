package com.motiolab.nabusi_server.mobileAuthCode.application;

import com.motiolab.nabusi_server.mobileAuthCode.application.dto.request.SendMobileAuthCodeRequestV1;
import com.motiolab.nabusi_server.mobileAuthCode.domain.MobileAuthCodeEntity;
import com.motiolab.nabusi_server.mobileAuthCode.domain.MobileAuthCodeRepository;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.SmsService;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsBodyRequest;
import com.motiolab.nabusi_server.notificationPackage.notificationSms.application.dto.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MobileAuthCodeServiceImpl implements MobileAuthCodeService{
    private static final Logger log = LoggerFactory.getLogger(MobileAuthCodeServiceImpl.class);
    private final SmsService smsService;
    private final MobileAuthCodeRepository mobileAuthCodeRepository;

    @Override
    public SendSmsResponse sendMobileAuthCode(SendMobileAuthCodeRequestV1 sendMobileAuthCodeRequestV1) {
        List<SendSmsBodyRequest.Message> messages = new ArrayList<>();
        String authCode = generateRandomNumber();
        final String content = "[모티오] 인증번호 [" + authCode + "]를 입력해주세요.";
        final String subject = "인증번호";

        messages.add(SendSmsBodyRequest.Message.builder()
                .to(sendMobileAuthCodeRequestV1.getMobile())
                .content(content)
                .subject(subject)
                .build());

        SendSmsBodyRequest sendSmsBodyRequest = SendSmsBodyRequest.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode(sendMobileAuthCodeRequestV1.getCountryCode())
                .from("0205457633")
                .content(content)
                .messages(messages)
                .build();

        final SendSmsResponse sendSmsResponse = smsService.send(sendSmsBodyRequest);
        if(sendSmsResponse.getStatusCode().equals("202")) {
            MobileAuthCodeEntity mobileAuthCodeEntity = MobileAuthCodeEntity.create(
                    sendMobileAuthCodeRequestV1.getCountryCode(),
                    sendMobileAuthCodeRequestV1.getMobile(),
                    authCode,
                    LocalDateTime.now().plusMinutes(5));

            mobileAuthCodeRepository.save(mobileAuthCodeEntity);
        }
        return sendSmsResponse;
    }

    @Override
    public Boolean verificationMobileAuthCode(String authCode) {
        final Optional<MobileAuthCodeEntity> optionalMobileAuthCodeEntity = mobileAuthCodeRepository.findByAuthCode(authCode);
        if (optionalMobileAuthCodeEntity.isEmpty() || optionalMobileAuthCodeEntity.get().getExpireDateTime().isBefore(LocalDateTime.now())){
            log.error("SmsAuthNumber is Not match");
            return false;
        } else {
            return true;
        }
    }

    private String generateRandomNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // 0부터 9까지의 무작위 숫자 생성
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}
