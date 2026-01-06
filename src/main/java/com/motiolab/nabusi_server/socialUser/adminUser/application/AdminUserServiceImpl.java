package com.motiolab.nabusi_server.socialUser.adminUser.application;

import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.security.JwtTokenProvider;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request.AdminUserLoginRequest;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.request.AdminUserSignupRequest;
import com.motiolab.nabusi_server.socialUser.adminUser.application.dto.response.AdminUserLoginResponse;
import com.motiolab.nabusi_server.socialUser.adminUser.domain.AdminUserEntity;
import com.motiolab.nabusi_server.socialUser.adminUser.domain.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void signup(AdminUserSignupRequest adminUserSignupRequest) {
        // ... (existing code remains same)
        if (adminUserRepository.findByEmail(adminUserSignupRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Already registered email.");
        }

        MemberDto memberDto = MemberDto.builder()
                .name(adminUserSignupRequest.getName())
                .email(adminUserSignupRequest.getEmail())
                .mobile(adminUserSignupRequest.getPhoneNumber())
                .socialName("admin")
                .roleList(Collections.emptyList())
                .centerIdList(Collections.singletonList(1L))
                .build();

        MemberDto savedMember = memberService.create(memberDto);

        AdminUserEntity adminUserEntity = AdminUserEntity.create(
                adminUserSignupRequest.getEmail(),
                passwordEncoder.encode(adminUserSignupRequest.getPassword()),
                adminUserSignupRequest.getBirthYear(),
                adminUserSignupRequest.getBirthDay(),
                adminUserSignupRequest.getGender(),
                adminUserSignupRequest.getPhoneNumber(),
                savedMember.getId());

        adminUserRepository.save(adminUserEntity);
    }

    @Override
    public AdminUserLoginResponse login(AdminUserLoginRequest adminUserLoginRequest) {
        AdminUserEntity adminUserEntity = adminUserRepository.findByEmail(adminUserLoginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!passwordEncoder.matches(adminUserLoginRequest.getPassword(), adminUserEntity.getPassword())) {
            return AdminUserLoginResponse.builder()
                    .isSuccessLogin(false)
                    .message("Invalid password.")
                    .build();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(adminUserEntity.getMemberId());
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        return AdminUserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(adminUserEntity.getEmail())
                .isSuccessLogin(true)
                .message("Login successful.")
                .build();
    }
}
