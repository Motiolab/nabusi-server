package com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserLoginRequest {
    private String email;
    private String password;
}
