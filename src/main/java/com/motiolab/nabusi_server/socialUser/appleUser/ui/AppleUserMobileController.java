package com.motiolab.nabusi_server.socialUser.appleUser.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.motiolab.nabusi_server.socialUser.appleUser.application.AppleUserMobileService;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleSignupDto;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleUserMobileDto;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.request.AppleLoginMobileRequestV1;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.request.AppleSignupMobileRequestV1;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.response.AppleSignupMobileResponseV1;
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
public class AppleUserMobileController {
    private static final Logger log = LoggerFactory.getLogger(AppleUserMobileController.class);
    private final AppleUserMobileService appleUserMobileService;

    @PostMapping("/v1/mobile/apple-user/login")
    public ResponseEntity<AppleUserMobileDto> appleLogin(@RequestBody AppleLoginMobileRequestV1 appleLoginMobileRequestV1) throws JsonProcessingException {
        final AppleUserMobileDto appleUserMobileDto = appleUserMobileService.appleLogin(appleLoginMobileRequestV1.getAuthorizationCode());

        log.info("appleUserMobileDto : {}", appleUserMobileDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + appleUserMobileDto.getAccessToken())
                .header("Set-Cookie", appleUserMobileDto.getRefreshToken())
                .body(appleUserMobileDto);
    }

    @PostMapping("/v1/mobile/apple-user/signup")
    public ResponseEntity<AppleSignupMobileResponseV1> appleSignup(@RequestBody AppleSignupMobileRequestV1 appleSignupMobileRequestV1) {
        final AppleSignupDto appleSignupDto = appleUserMobileService.appleSignup(appleSignupMobileRequestV1);

        log.info("appleSignupDto : {}", appleSignupDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + appleSignupDto.getAccessToken())
                .header("Set-Cookie", appleSignupDto.getRefreshToken())
                .body(AppleSignupMobileResponseV1.builder().success(true).build());
    }
}
