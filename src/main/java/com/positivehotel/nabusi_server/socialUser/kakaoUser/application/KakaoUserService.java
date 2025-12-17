package com.positivehotel.nabusi_server.socialUser.kakaoUser.application;

import com.positivehotel.nabusi_server.socialUser.kakaoUser.application.dto.KakaoUserDto;
import org.springframework.lang.NonNull;

public interface KakaoUserService {
    Boolean post(@NonNull KakaoUserDto kakaoUserDto);
    Boolean patch(@NonNull KakaoUserDto kakaoUserDto);
    KakaoUserDto getByPhoneNumber(@NonNull String phoneNumber);
    KakaoUserDto getByMemberId(Long memberId);
}
