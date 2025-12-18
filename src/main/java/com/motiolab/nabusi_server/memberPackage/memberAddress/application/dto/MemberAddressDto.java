package com.motiolab.nabusi_server.memberPackage.memberAddress.application.dto;

import com.motiolab.nabusi_server.memberPackage.memberAddress.domain.MemberAddressEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
public class MemberAddressDto {
    private Long id;
    private Long memberId;
    private String name;
    private String address;
    private String detailAddress;
    private String recipient;
    private String mobile;
    private String zipCode;
    private String roadName;
    private String isDefault;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static MemberAddressDto from(MemberAddressEntity memberAddressEntity) {
        return MemberAddressDto.builder()
                .id(memberAddressEntity.getId())
                .memberId(memberAddressEntity.getMemberId())
                .name(memberAddressEntity.getName())
                .address(memberAddressEntity.getAddress())
                .detailAddress(memberAddressEntity.getDetailAddress())
                .recipient(memberAddressEntity.getRecipient())
                .mobile(memberAddressEntity.getMobile())
                .zipCode(memberAddressEntity.getZipCode())
                .roadName(memberAddressEntity.getRoadName())
                .isDefault(memberAddressEntity.getIsDefault())
                .lastUpdatedDate(memberAddressEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(memberAddressEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
