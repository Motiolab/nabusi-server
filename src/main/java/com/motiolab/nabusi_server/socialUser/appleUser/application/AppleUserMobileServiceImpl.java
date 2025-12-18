package com.motiolab.nabusi_server.socialUser.appleUser.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.role.application.RoleService;
import com.motiolab.nabusi_server.role.application.dto.RoleDto;
import com.motiolab.nabusi_server.security.JwtTokenProvider;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleSignupDto;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleUserDto;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.AppleUserMobileDto;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.request.AppleSignupMobileRequestV1;
import com.motiolab.nabusi_server.socialUser.appleUser.application.dto.response.AppleAuthTokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppleUserMobileServiceImpl implements AppleUserMobileService {
    private static final Logger log = LoggerFactory.getLogger(AppleUserMobileServiceImpl.class);

    @Value("${apple.auth.url}")
    private String authUrl;

    @Value("${apple.team.id}")
    private String teamId;

    @Value("${apple.redirect.url}")
    private String redirectUrl;

    @Value("${apple.client.id}")
    private String clientId;

    @Value("${apple.key.id}")
    private String keyId;

    @Value("${apple.key.path}")
    private String keyPath;

    private final AppleUserService appleUserService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleService roleService;

    @Override
    public AppleUserMobileDto appleLogin(String authorizationCode) throws JsonProcessingException {
        final String requestUrl = authUrl + "/auth/token";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", makeClientSecret());
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);

        body.add("scope", "email");

        final HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        final AppleAuthTokenResponse response = restTemplate.postForObject(requestUrl, request,
                AppleAuthTokenResponse.class);

        String token = response.getId_token();
        log.info("token : " + token);
        String[] check = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(check[1]));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> returnMap = mapper.readValue(payload, new TypeReference<>() {
        });

        returnMap.forEach((key, value) -> System.out.println("key : " + key + ", value : " + value));
        log.info(returnMap.toString());

        final String email = returnMap.get("email") == null ? "" : returnMap.get("email").toString();
        final String sub = returnMap.get("sub") == null ? "" : returnMap.get("sub").toString();

        final AppleUserDto appleUserDto = appleUserService.getBySub(sub);

        if (appleUserDto == null) {
            return AppleUserMobileDto.builder()
                    .token(response.getRefresh_token())
                    .message("회원가입을 진행해주세요. 애플 회원가입 화면으로 이동")
                    .email(email)
                    .sub(sub)
                    .isSuccessLogin(false)
                    .build();
        } else {
            final MemberDto memberDto = memberService.getById(appleUserDto.getMemberId());
            if (memberDto == null) {
                throw new NotFoundException(MemberDto.class, appleUserDto.getMemberId());
            }
            return AppleUserMobileDto.builder()
                    .accessToken(jwtTokenProvider.generateAccessToken(memberDto.getId()))
                    .refreshToken(jwtTokenProvider.generateRefreshToken())
                    .email(email)
                    .sub(sub)
                    .isSuccessLogin(true)
                    .build();
        }
    }

    @Override
    public AppleSignupDto appleSignup(AppleSignupMobileRequestV1 appleSignupMobileRequestV1) {
        final MemberDto existsMemberDto = memberService.getByMobileAndSocialName(appleSignupMobileRequestV1.getMobile(),
                "apple");
        if (existsMemberDto != null) {
            log.error("이미 존재하는 회원입니다.");
            return AppleSignupDto.builder().build();
        }

        final Optional<AppleUserDto> optionalAppleUserDto = appleUserService
                .getByMobile(appleSignupMobileRequestV1.getMobile());
        if (optionalAppleUserDto.isPresent()) {
            log.error("이미 Apple 유저에 존재합니다.");
            return AppleSignupDto.builder().build();
        }

        final RoleDto roleDto = roleService.getByName("NABUSI_USER")
                .orElseThrow(() -> new RuntimeException("ROLE NOT FOUND"));
        final MemberDto memberDto = MemberDto.builder()
                .name(appleSignupMobileRequestV1.getName())
                .mobile(appleSignupMobileRequestV1.getCountryCode() + " " + appleSignupMobileRequestV1.getMobile())
                .socialName("apple")
                .roleList(List.of(roleDto))
                .build();
        final MemberDto newMemberDto = memberService.create(memberDto);

        final AppleUserDto appleUserDto = AppleUserDto.builder()
                .userId(appleSignupMobileRequestV1.getUserId())
                .mobile(appleSignupMobileRequestV1.getCountryCode() + " " + appleSignupMobileRequestV1.getMobile())
                .sub(appleSignupMobileRequestV1.getSub())
                .memberId(newMemberDto.getId())
                .build();
        appleUserService.post(appleUserDto);

        final String accessToken = jwtTokenProvider.generateAccessToken(newMemberDto.getId());
        final String refreshToken = jwtTokenProvider.generateRefreshToken();
        return AppleSignupDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    private String makeClientSecret() {
        log.info("Apple Login Props: teamId={}, clientId={}, keyId={}, keyPath={}", teamId, clientId, keyId, keyPath);

        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);
        return Jwts.builder()
                .setHeaderParam("kid", keyId)
                .setHeaderParam("alg", "ES256")
                .setIssuer(teamId)
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
                .setAudience(authUrl)
                .setSubject(clientId)
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            ClassPathResource classPathResource = new ClassPathResource(keyPath);

            if (!classPathResource.exists()) {
                throw new IllegalArgumentException();
            }
            String privateKey;
            try (InputStream is = new BufferedInputStream(classPathResource.getInputStream())) {
                // logic...
                privateKey = IOUtils.toString(is, StandardCharsets.UTF_8);
                System.out.println(privateKey);
            }
            Reader pemReader = new StringReader(privateKey);

            try (PEMParser pemParser = new PEMParser(pemReader)) {
                Object object = pemParser.readObject();
                if (object instanceof PrivateKeyInfo privateKeyInfo) {
                    return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
                } else {
                    throw new IllegalArgumentException("Invalid private key format");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting private key from String", e);
        }
    }
}
