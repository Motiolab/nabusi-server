package com.motiolab.nabusi_server.socialUser.naverUser.application.dto;

import com.motiolab.nabusi_server.socialUser.naverUser.domain.NaverUserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class NaverUserDto {
    private Long id;
    private String gender;
    private String email;
    private String mobile;
    private String mobileE164;
    private String name;
    private String birthday;
    private String birthyear;
    private Long memberId;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    @Builder
    public static NaverUserDto from(NaverUserEntity naverUserEntity) {
        return NaverUserDto.builder()
                .id(naverUserEntity.getId())
                .gender(naverUserEntity.getGender())
                .email(naverUserEntity.getEmail())
                .mobile(naverUserEntity.getMobile())
                .mobileE164(naverUserEntity.getMobileE164())
                .name(naverUserEntity.getName())
                .birthday(naverUserEntity.getBirthday())
                .birthyear(naverUserEntity.getBirthyear())
                .memberId(naverUserEntity.getMemberId())
                .lastUpdatedDate(naverUserEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(naverUserEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
