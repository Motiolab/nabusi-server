package com.motiolab.nabusi_server.socialUser.kakaoUser.application.dto;

import com.motiolab.nabusi_server.socialUser.kakaoUser.domain.KakaoUserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class KakaoUserDto {
    private Long id;
    private Long userId;
    private String email;
    private String birthDay;
    private String birthYear;
    private String gender;
    private String phoneNumber;
    private String name;
    private String nickName;
    private Long memberId;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static KakaoUserDto from(KakaoUserEntity kakaoUserEntity) {
        return KakaoUserDto.builder()
                .id(kakaoUserEntity.getId())
                .userId(kakaoUserEntity.getUserId())
                .email(kakaoUserEntity.getEmail())
                .birthDay(kakaoUserEntity.getBirthDay())
                .birthYear(kakaoUserEntity.getBirthYear())
                .gender(kakaoUserEntity.getGender())
                .phoneNumber(kakaoUserEntity.getPhoneNumber())
                .name(kakaoUserEntity.getName())
                .nickName(kakaoUserEntity.getNickName())
                .memberId(kakaoUserEntity.getMemberId())
                .lastUpdatedDate(kakaoUserEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(kakaoUserEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
