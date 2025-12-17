package com.positivehotel.nabusi_server.memberPackage.memberPoint.application.dto;

import com.positivehotel.nabusi_server.memberPackage.memberPoint.domain.MemberPointEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
public class MemberPointDto {
    private Long id;
    private Long memberId;
    private Long point;
    private ZonedDateTime createdDate;

    public static MemberPointDto from(MemberPointEntity memberPointEntity) {
        return MemberPointDto.builder()
                .id(memberPointEntity.getId())
                .memberId(memberPointEntity.getMemberId())
                .point(memberPointEntity.getPoint())
                .createdDate(memberPointEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
