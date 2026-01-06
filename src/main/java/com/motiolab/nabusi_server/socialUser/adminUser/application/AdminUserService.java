package com.motiolab.nabusi_server.socialUser.adminUser.application;

import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request.AdminUserLoginRequest;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request.AdminUserSignupRequest;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.response.AdminUserLoginResponse;

public interface AdminUserService {
    void signup(AdminUserSignupRequest adminUserSignupRequest);

    AdminUserLoginResponse login(AdminUserLoginRequest adminUserLoginRequest);
}
