package com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserSignupRequest {
    private String email;
    private String password;
    private String name;
    private String birthYear;
    private String birthDay;
    private String gender;
    private String phoneNumber;
}
