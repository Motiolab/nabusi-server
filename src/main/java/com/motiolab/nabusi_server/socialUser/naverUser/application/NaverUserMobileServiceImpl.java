package com.motiolab.nabusi_server.socialUser.naverUser.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motiolab.nabusi_server.exception.customException.ExternalServiceException;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.role.application.RoleService;
import com.motiolab.nabusi_server.role.application.dto.RoleDto;
import com.motiolab.nabusi_server.security.DynamicRoleService;
import com.motiolab.nabusi_server.security.JwtTokenProvider;
import com.motiolab.nabusi_server.socialUser.naverToken.application.NaverTokenService;
import com.motiolab.nabusi_server.socialUser.naverToken.application.dto.NaverTokenDto;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.NaverUserDto;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.NaverUserMobileDto;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.request.NaverLoginMobileRequestV1;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.response.NaverOAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NaverUserMobileServiceImpl implements NaverUserMobileService{
    private final MemberService memberService;
    private final RoleService roleService;
    private final NaverUserService naverUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final DynamicRoleService dynamicRoleService;
    private final NaverTokenService naverTokenService;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    private static final String NAVER_API_URL = "https://openapi.naver.com/v1/nid/me";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public NaverUserMobileDto naverLogin(NaverLoginMobileRequestV1 naverLoginMobileRequestV1) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + naverLoginMobileRequestV1.getNaverAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(NAVER_API_URL, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            NaverOAuth2Response naverOAuth2Response = new ObjectMapper().readValue(response.getBody(), NaverOAuth2Response.class);
            final Optional<NaverUserDto> optionalNaverUserDto = naverUserService.getByMobileE164(naverOAuth2Response.getResponse().getMobileE164());
            final String memberMobile = getMemberMobile(naverOAuth2Response);
            final MemberDto memberDtoByMobileAndSocialName = memberService.getByMobileAndSocialName(memberMobile, "naver");

            if(optionalNaverUserDto.isPresent() && memberDtoByMobileAndSocialName != null) {
                final NaverUserDto existsNaverUserDto = optionalNaverUserDto.get();
                existsNaverUserDto.setGender(naverOAuth2Response.getResponse().getGender());
                existsNaverUserDto.setEmail(naverOAuth2Response.getResponse().getEmail());
                existsNaverUserDto.setMobile(naverOAuth2Response.getResponse().getMobile());
                existsNaverUserDto.setMobileE164(naverOAuth2Response.getResponse().getMobileE164());
                existsNaverUserDto.setName(naverOAuth2Response.getResponse().getName());
                existsNaverUserDto.setBirthday(naverOAuth2Response.getResponse().getBirthday());
                existsNaverUserDto.setBirthyear(naverOAuth2Response.getResponse().getBirthyear());
                existsNaverUserDto.setMemberId(memberDtoByMobileAndSocialName.getId());

                naverUserService.patch(existsNaverUserDto);
                createMobileNaverToken(memberDtoByMobileAndSocialName.getId(), naverLoginMobileRequestV1);

                final String accessToken = jwtTokenProvider.generateAccessToken(existsNaverUserDto.getMemberId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();
                return NaverUserMobileDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
            } else {
                final RoleDto roleDto = roleService.getByName("NABUSI_USER").orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));
                final MemberDto memberDto = MemberDto.builder()
                        .name(naverOAuth2Response.getResponse().getName())
                        .email(naverOAuth2Response.getResponse().getEmail())
                        .mobile(getMemberMobile(naverOAuth2Response))
                        .socialName("naver")
                        .roleList(List.of(roleDto))
                        .build();
                final MemberDto savedMemberDto = memberService.create(memberDto);
                dynamicRoleService.updateMember(savedMemberDto);

                final NaverUserDto naverUserDto = NaverUserDto.builder()
                        .gender(naverOAuth2Response.getResponse().getGender())
                        .email(naverOAuth2Response.getResponse().getEmail())
                        .mobile(naverOAuth2Response.getResponse().getMobile())
                        .mobileE164(naverOAuth2Response.getResponse().getMobileE164())
                        .name(naverOAuth2Response.getResponse().getName())
                        .birthday(naverOAuth2Response.getResponse().getBirthday())
                        .birthyear(naverOAuth2Response.getResponse().getBirthyear())
                        .memberId(savedMemberDto.getId())
                        .build();

                naverUserService.post(naverUserDto);
                dynamicRoleService.updateAllMemberEntityList();
                createMobileNaverToken(savedMemberDto.getId(), naverLoginMobileRequestV1);

                final String accessToken = jwtTokenProvider.generateAccessToken(savedMemberDto.getId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();
                return NaverUserMobileDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
            }
        } else {
            throw new ExternalServiceException(NaverUserMobileServiceImpl.class, NAVER_API_URL);
        }
    }

    @Override
    public void resignNaverUser(String accessToken) {
        String url = "https://nid.naver.com/oauth2.0/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "delete");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("access_token", accessToken);
        body.add("service_provider","NAVER");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        restTemplate.postForObject(url, request, String.class);
    }

    private void createMobileNaverToken(Long memberId, NaverLoginMobileRequestV1 naverLoginMobileRequestV1) {
        final NaverTokenDto naverTokenDto = naverTokenService.getByMemberIdAndAccessToken(memberId, naverLoginMobileRequestV1.getNaverAccessToken());
        if(naverTokenDto == null) {
            NaverTokenDto newNaverTokenDto = NaverTokenDto.builder()
                    .accessToken(naverLoginMobileRequestV1.getNaverAccessToken())
                    .expiresAt(naverLoginMobileRequestV1.getNaverAccessTokenExpireAt())
                    .refreshToken(naverLoginMobileRequestV1.getRefreshToken())
                    .issuedAt(LocalDateTime.now())
                    .memberId(memberId)
                    .build();
            naverTokenService.create(newNaverTokenDto);
        } else {
            naverTokenDto.setAccessToken(naverLoginMobileRequestV1.getNaverAccessToken());
            naverTokenDto.setExpiresAt(naverLoginMobileRequestV1.getNaverAccessTokenExpireAt());
            naverTokenDto.setRefreshToken(naverLoginMobileRequestV1.getRefreshToken());
            naverTokenDto.setIssuedAt(LocalDateTime.now());
            naverTokenService.patch(naverTokenDto);
        }
    }

    private String getMemberMobile(NaverOAuth2Response attributes) {
        String mobile = attributes.getResponse().getMobile().replaceAll("-", "");
        String mobileE164 = attributes.getResponse().getMobileE164();
        String countryCode = mobileE164.replace(mobile.substring(1), "");
        return countryCode + " " + mobile;
    }
}
