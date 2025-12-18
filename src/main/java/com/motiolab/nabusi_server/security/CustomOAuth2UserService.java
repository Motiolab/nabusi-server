package com.motiolab.nabusi_server.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.role.application.RoleService;
import com.motiolab.nabusi_server.role.application.dto.RoleDto;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.response.AppleOAuth2Response;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.KakaoUserService;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserDto;
import com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto.response.KakaoOAuth2Response;
import com.motiolab.nabusi_server.socialUser.naverUser.application.NaverUserService;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.NaverUserDto;
import com.motiolab.nabusi_server.socialUser.naverUser.application.dto.response.NaverOAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final KakaoUserService kakaoUserService;
    private final NaverUserService naverUserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final ObjectMapper objectMapper;
    private final RoleService roleService;
    private final DynamicRoleService dynamicRoleService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (Objects.equals("kakao", registrationId)) {
            final OAuth2User oAuth2User = super.loadUser(userRequest);
            final KakaoOAuth2Response attributes = objectMapper.convertValue(oAuth2User.getAttributes(), KakaoOAuth2Response.class);
            final String nameAttributeKey = new PropertyNamingStrategies.SnakeCaseStrategy().translate(attributes.getKakaoAccount().getClass().getSimpleName());

            final KakaoUserDto existsKakaoUserDto = kakaoUserService.getByPhoneNumber(attributes.getKakaoAccount().getPhoneNumber());
            final String memberMobile = getMemberMobile(attributes);
            final MemberDto memberDtoByMobileAndSocialName = memberService.getByMobileAndSocialName(memberMobile, registrationId);

            if(existsKakaoUserDto != null && memberDtoByMobileAndSocialName != null){
                existsKakaoUserDto.setUserId(attributes.getId());
                existsKakaoUserDto.setEmail(attributes.getKakaoAccount().getEmail());
                existsKakaoUserDto.setBirthDay(attributes.getKakaoAccount().getBirthday());
                existsKakaoUserDto.setBirthYear(attributes.getKakaoAccount().getBirthyear());
                existsKakaoUserDto.setGender(attributes.getKakaoAccount().getGender());
                existsKakaoUserDto.setPhoneNumber(attributes.getKakaoAccount().getPhoneNumber());
                existsKakaoUserDto.setName(attributes.getKakaoAccount().getName());
                existsKakaoUserDto.setNickName(attributes.getKakaoAccount().getProfile().getNickname());
                existsKakaoUserDto.setMemberId(memberDtoByMobileAndSocialName.getId());

                kakaoUserService.patch(existsKakaoUserDto);

                final String accessToken = jwtTokenProvider.generateAccessToken(existsKakaoUserDto.getMemberId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();
                return new CustomOAuth2UserWithToken(memberDtoByMobileAndSocialName.getRoleList().stream().map(RoleDto::getId).map(roleId -> new SimpleGrantedAuthority(roleId.toString())).toList(), oAuth2User.getAttributes(), nameAttributeKey, accessToken, refreshToken);
            } else {
                final RoleDto roleDto = roleService.getByName("NABUSI_USER").orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));
                final MemberDto memberDto = MemberDto.builder()
                        .name(attributes.getKakaoAccount().getName())
                        .email(attributes.getKakaoAccount().getEmail())
                        .mobile(getMemberMobile(attributes))
                        .socialName(registrationId)
                        .roleList(List.of(roleDto))
                        .build();
                final MemberDto savedMemberDto = memberService.create(memberDto);
                dynamicRoleService.updateMember(savedMemberDto);

                final KakaoUserDto kakaoUserDto = KakaoUserDto.builder()
                        .userId(attributes.getId())
                        .email(attributes.getKakaoAccount().getEmail())
                        .birthDay(attributes.getKakaoAccount().getBirthday())
                        .birthYear(attributes.getKakaoAccount().getBirthyear())
                        .gender(attributes.getKakaoAccount().getGender())
                        .phoneNumber(attributes.getKakaoAccount().getPhoneNumber())
                        .name(attributes.getKakaoAccount().getName())
                        .nickName(attributes.getKakaoAccount().getProfile().getNickname())
                        .memberId(savedMemberDto.getId())
                        .build();

                kakaoUserService.post(kakaoUserDto);
                dynamicRoleService.updateAllMemberEntityList();

                final String accessToken = jwtTokenProvider.generateAccessToken(savedMemberDto.getId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();
                return new CustomOAuth2UserWithToken(memberDto.getRoleList().stream().map(RoleDto::getId).map(roleId -> new SimpleGrantedAuthority(roleId.toString())).toList(), oAuth2User.getAttributes(), nameAttributeKey, accessToken, refreshToken);
            }
        } else if (Objects.equals("naver", registrationId)) {
            final OAuth2User oAuth2User = super.loadUser(userRequest);
            final NaverOAuth2Response attributes = objectMapper.convertValue(oAuth2User.getAttributes(), NaverOAuth2Response.class);
            final String nameAttributeKey = new PropertyNamingStrategies.SnakeCaseStrategy().translate(attributes.getResponse().getClass().getSimpleName());

            final Optional<NaverUserDto> optionalNaverUserDto = naverUserService.getByMobileE164(attributes.getResponse().getMobileE164());
            final String memberMobile = getMemberMobile(attributes);
            final MemberDto memberDtoByMobileAndSocialName = memberService.getByMobileAndSocialName(memberMobile, registrationId);

            if (optionalNaverUserDto.isPresent() &&  memberDtoByMobileAndSocialName != null) {
                final NaverUserDto naverUserDto = optionalNaverUserDto.get();
                naverUserDto.setGender(attributes.getResponse().getGender());
                naverUserDto.setEmail(attributes.getResponse().getEmail());
                naverUserDto.setMobile(attributes.getResponse().getMobile());
                naverUserDto.setMobileE164(attributes.getResponse().getMobileE164());
                naverUserDto.setName(attributes.getResponse().getName());
                naverUserDto.setBirthday(attributes.getResponse().getBirthday());
                naverUserDto.setBirthyear(attributes.getResponse().getBirthyear());

                naverUserService.patch(naverUserDto);

                final String accessToken = jwtTokenProvider.generateAccessToken(naverUserDto.getMemberId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();

                return new CustomOAuth2UserWithToken(memberDtoByMobileAndSocialName.getRoleList().stream().map(RoleDto::getId).map(roleId -> new SimpleGrantedAuthority(roleId.toString())).toList(), oAuth2User.getAttributes(), nameAttributeKey, accessToken, refreshToken);
            } else {
                final RoleDto roleDto = roleService.getByName("NABUSI_USER").orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));

                final MemberDto memberDto = MemberDto.builder()
                        .name(attributes.getResponse().getName())
                        .email(attributes.getResponse().getEmail())
                        .mobile(getMemberMobile(attributes))
                        .socialName(registrationId)
                        .roleList(List.of(roleDto))
                        .build();
                final MemberDto savedMemberDto = memberService.create(memberDto);
                dynamicRoleService.updateMember(savedMemberDto);
                final NaverUserDto naverUserDto = NaverUserDto.builder()
                        .gender(attributes.getResponse().getGender())
                        .email(attributes.getResponse().getEmail())
                        .mobile(attributes.getResponse().getMobile())
                        .mobileE164(attributes.getResponse().getMobileE164())
                        .name(attributes.getResponse().getName())
                        .birthday(attributes.getResponse().getBirthday())
                        .birthyear(attributes.getResponse().getBirthyear())
                        .memberId(savedMemberDto.getId())
                        .build();

                final Boolean isPost = naverUserService.post(naverUserDto);

                final String accessToken = jwtTokenProvider.generateAccessToken(savedMemberDto.getId());
                final String refreshToken = jwtTokenProvider.generateRefreshToken();
                return new CustomOAuth2UserWithToken(memberDto.getRoleList().stream().map(RoleDto::getId).map(roleId -> new SimpleGrantedAuthority(roleId.toString())).toList(), oAuth2User.getAttributes(), nameAttributeKey, accessToken, refreshToken);
            }
        } else if (Objects.equals("apple", registrationId)) {
            String idToken = userRequest.getAdditionalParameters().get("id_token").toString();
            String[] parts = idToken.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
            try {
                final Map<String , Object> attributesMap = objectMapper.readValue(decodedBytes, new TypeReference<>() {});
                final AppleOAuth2Response attributes = objectMapper.readValue(decodedBytes, new TypeReference<>() {});
                final String nameAttributeKey = new PropertyNamingStrategies.SnakeCaseStrategy().translate(attributes.getClass().getSimpleName());

                return new CustomOAuth2UserWithToken(Stream.of("ROLE_USER").map(SimpleGrantedAuthority::new).toList(), Map.of(nameAttributeKey, attributesMap), nameAttributeKey, "", "");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private String getMemberMobile(NaverOAuth2Response attributes) {
        String mobile = attributes.getResponse().getMobile().replaceAll("-", "");
        String mobileE164 = attributes.getResponse().getMobileE164();
        String countryCode = mobileE164.replace(mobile.substring(1), "");
        return countryCode + " " + mobile;
    }

    private String getMemberMobile(KakaoOAuth2Response attributes) {
        String kakaoMobile = attributes.getKakaoAccount().getPhoneNumber().replaceAll("-", "");
        String[] parts = kakaoMobile.split(" ", 2);
        parts[1] = "0" + parts[1];
        return String.join(" ", parts);
    }
}