package com.positivehotel.nabusi_server.memberPackage.member.application.dto;

import com.positivehotel.nabusi_server.memberPackage.member.domain.MemberEntity;
import com.positivehotel.nabusi_server.role.application.dto.RoleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
@Setter
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String socialName;
    private List<Long> centerIdList;
    private List<RoleDto> roleList;
    private ZonedDateTime createdDate;

    public static MemberDto from(MemberEntity memberEntity) {
        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .email(memberEntity.getEmail())
                .mobile(memberEntity.getMobile())
                .socialName(memberEntity.getSocialName())
                .centerIdList(memberEntity.getCenterIdList())
                .roleList(memberEntity.getRoles().stream().map(RoleDto::from).toList())
                .createdDate(memberEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
