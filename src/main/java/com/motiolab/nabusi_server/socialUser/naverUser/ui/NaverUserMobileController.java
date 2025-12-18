package com.motiolab.nabusi_server.socialUser.naverUser.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.motiolab.nabusi_server.socialUser.naverUser.application.NaverUserMobileService;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.NaverUserMobileDto;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.request.NaverLoginMobileRequestV1;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.response.NaverLoginMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NaverUserMobileController {
    private final NaverUserMobileService naverUserMobileService;

    @PostMapping("/v1/mobile/naver-user/login")
    public ResponseEntity<NaverLoginMobileResponse> naverLoginMobile(@RequestBody NaverLoginMobileRequestV1 naverLoginMobileRequestV1) throws JsonProcessingException {
        final NaverUserMobileDto naverUserMobileDto = naverUserMobileService.naverLogin(naverLoginMobileRequestV1);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + naverUserMobileDto.getAccessToken())
                .header("Set-Cookie", naverUserMobileDto.getRefreshToken())
                .body(NaverLoginMobileResponse.builder().success(true).build());
    }
}
