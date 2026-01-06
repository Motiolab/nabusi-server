package com.motiolab.nabusi_server.socialUser.adminUser.ui;

import com.motiolab.nabusi_server.socialUser.adminUser.application.AdminUserService;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request.AdminUserLoginRequest;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request.AdminUserSignupRequest;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.response.AdminUserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminUserMobileController {
    private final AdminUserService adminUserService;

    @PostMapping("/v1/mobile/admin-user/signup")
    public ResponseEntity<Void> signup(@RequestBody AdminUserSignupRequest adminUserSignupRequest) {
        adminUserService.signup(adminUserSignupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/v1/mobile/admin-user/login")
    public ResponseEntity<AdminUserLoginResponse> login(@RequestBody AdminUserLoginRequest adminUserLoginRequest) {
        AdminUserLoginResponse response = adminUserService.login(adminUserLoginRequest);
        if (!response.getIsSuccessLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + response.getAccessToken())
                .header("Set-Cookie", response.getRefreshToken())
                .body(response);
    }
}
