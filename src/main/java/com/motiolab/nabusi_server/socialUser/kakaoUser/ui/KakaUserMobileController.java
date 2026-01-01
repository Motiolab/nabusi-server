package com.motiolab.nabusi_server.socialUser.kakaoUser.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.motiolab.nabusi_server.socialUser.appleUser.ui.AppleUserMobileController;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.KakaoUserMobileService;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserMobileDto;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.request.KakaoLoginMobileRequestV1;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.response.KakaoLoginMobileResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class KakaUserMobileController {
    private static final Logger log = LoggerFactory.getLogger(AppleUserMobileController.class);
    private final KakaoUserMobileService kakaoUserMobileService;

    @PostMapping("/v1/mobile/kakao-user/login")
    public ResponseEntity<KakaoLoginMobileResponse> kakaoLoginMobile(
            @RequestBody KakaoLoginMobileRequestV1 kakaoLoginMobileRequestV1) throws JsonProcessingException {
        final KakaoUserMobileDto kakaoUserMobileDto = kakaoUserMobileService
                .kakaoLogin(kakaoLoginMobileRequestV1.getKakaoAccessToken());

        log.info("kakaoUserMobileDto : {}", kakaoUserMobileDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + kakaoUserMobileDto.getAccessToken())
                .header("Set-Cookie", kakaoUserMobileDto.getRefreshToken())
                .body(KakaoLoginMobileResponse.builder().success(true).build());
    }
}
