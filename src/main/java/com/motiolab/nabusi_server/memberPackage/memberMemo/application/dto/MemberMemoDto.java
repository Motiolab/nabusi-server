package com.motiolab.nabusi_server.memberPackage.memberMemo.application.dto;

import com.motiolab.nabusi_server.memberPackage.memberMemo.domain.MemberMemoEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
public class MemberMemoDto {
    private Long id;
    private String content;
    private Long memberId;
    private Long registerId;
    private ZonedDateTime createdDate;

    public static MemberMemoDto from(MemberMemoEntity memberMemoEntity) {
        return MemberMemoDto.builder()
                .id(memberMemoEntity.getId())
                .content(memberMemoEntity.getContent())
                .memberId(memberMemoEntity.getMemberId())
                .registerId(memberMemoEntity.getRegisterId())
                .createdDate(memberMemoEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
