package com.motiolab.nabusi_server.socialUser.kakaoUser.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motiolab.nabusi_server.exception.customException.ExternalServiceException;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.role.application.RoleService;
import com.motiolab.nabusi_server.role.application.dto.RoleDto;
import com.motiolab.nabusi_server.security.DynamicRoleService;
import com.motiolab.nabusi_server.security.JwtTokenProvider;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserDto;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserMobileDto;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.response.KakaoOAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoUserMobileServiceImpl implements KakaoUserMobileService{
    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;

    private final KakaoUserService kakaoUserService;
    private final MemberService memberService;
    private final RoleService roleService;
    private final DynamicRoleService dynamicRoleService;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/user/me";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public KakaoUserMobileDto kakaoLogin(String kakaoAccessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(KAKAO_API_URL, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Parse the Kakao response and return the user data
            KakaoOAuth2Response kakaoOAuth2Response = new ObjectMapper().readValue(response.getBody(), KakaoOAuth2Response.class);

            final KakaoUserDto existsKakaoUserDto = kakaoUserService.getByPhoneNumber(kakaoOAuth2Response.getKakaoAccount().getPhoneNumber());
            final String memberMobile = getMemberMobile(kakaoOAuth2Response);
            final MemberDto memberDtoByMobileAndSocialName = memberService.getByMobileAndSocialName(memberMobile, "kakao");

            if(existsKakaoUserDto != null && memberDtoByMobileAndSocialName != null) {
                existsKakaoUserDto.setUserId(kakaoOAuth2Response.getId());
                existsKakaoUserDto.setEmail(kakaoOAuth2Response.getKakaoAccount().getEmail());
                existsKakaoUserDto.setBirthDay(kakaoOAuth2Response.getKakaoAccount().getBirthday());
                existsKakaoUserDto.setBirthYear(kakaoOAuth2Response.getKakaoAccount().getBirthyear());
                existsKakaoUserDto.setGender(kakaoOAuth2Response.getKakaoAccount().getGender());
                existsKakaoUserDto.setPhoneNumber(kakaoOAuth2Response.getKakaoAccount().getPhoneNumber());
                existsKakaoUserDto.setName(kakaoOAuth2Response.getKakaoAccount().getName());
                existsKakaoUserDto.setNickName(kakaoOAuth2Response.getKakaoAccount().getProfile().getNickname());
                existsKakaoUserDto.setMemberId(memberDtoByMobileAndSocialName.getId());

                kakaoUserService.patch(existsKakaoUserDto);

                final String accessToken = jwtTokenProvider.generateAccessToken(existsKakaoUserDto.getMemberId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();
                return KakaoUserMobileDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
            }else {
                final RoleDto roleDto = roleService.getByName("NABUSI_USER").orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));
                final MemberDto memberDto = MemberDto.builder()
                        .name(kakaoOAuth2Response.getKakaoAccount().getName())
                        .email(kakaoOAuth2Response.getKakaoAccount().getEmail())
                        .mobile(getMemberMobile(kakaoOAuth2Response))
                        .socialName("kakao")
                        .roleList(List.of(roleDto))
                        .build();
                final MemberDto savedMemberDto = memberService.create(memberDto);
                dynamicRoleService.updateMember(savedMemberDto);

                final KakaoUserDto kakaoUserDto = KakaoUserDto.builder()
                        .userId(kakaoOAuth2Response.getId())
                        .email(kakaoOAuth2Response.getKakaoAccount().getEmail())
                        .birthDay(kakaoOAuth2Response.getKakaoAccount().getBirthday())
                        .birthYear(kakaoOAuth2Response.getKakaoAccount().getBirthyear())
                        .gender(kakaoOAuth2Response.getKakaoAccount().getGender())
                        .phoneNumber(kakaoOAuth2Response.getKakaoAccount().getPhoneNumber())
                        .name(kakaoOAuth2Response.getKakaoAccount().getName())
                        .nickName(kakaoOAuth2Response.getKakaoAccount().getProfile().getNickname())
                        .memberId(savedMemberDto.getId())
                        .build();

                kakaoUserService.post(kakaoUserDto);
                dynamicRoleService.updateAllMemberEntityList();

                final String accessToken = jwtTokenProvider.generateAccessToken(savedMemberDto.getId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();
                return KakaoUserMobileDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
            }
        } else {
            throw new ExternalServiceException(KakaoUserMobileServiceImpl.class, "https://kapi.kakao.com/v2/user/me");
        }
    }

    @Override
    public String delete(Long memberId) {
        KakaoUserDto kakaoUserDto = kakaoUserService.getByMemberId(memberId);

        String url = "https://kapi.kakao.com/v1/user/unlink";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "KakaoAK " + kakaoAdminKey);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target_id_type", "user_id");
        body.add("target_id", kakaoUserDto.getUserId().toString());

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, request, String.class);
    }

    private String getMemberMobile(KakaoOAuth2Response attributes) {
        String kakaoMobile = attributes.getKakaoAccount().getPhoneNumber().replaceAll("-", "");
        String[] parts = kakaoMobile.split(" ", 2);
        parts[1] = "0" + parts[1];
        return String.join(" ", parts);
    }
}
