package com.positivehotel.nabusi_server.socialUser.appleUser.application.dto;

import com.positivehotel.nabusi_server.socialUser.appleUser.domain.AppleUserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class AppleUserDto {
    private Long id;
    private String userId;
    private String email;
    private String mobile;
    private String nickName;
    private String fullName;
    private String sub;
    private Long memberId;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static AppleUserDto from(AppleUserEntity appleUserEntity) {
        return AppleUserDto.builder()
                .id(appleUserEntity.getId())
                .userId(appleUserEntity.getUserId())
                .email(appleUserEntity.getEmail())
                .mobile(appleUserEntity.getMobile())
                .nickName(appleUserEntity.getNickName())
                .fullName(appleUserEntity.getFullName())
                .sub(appleUserEntity.getSub())
                .memberId(appleUserEntity.getMemberId())
                .lastUpdatedDate(appleUserEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(appleUserEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
