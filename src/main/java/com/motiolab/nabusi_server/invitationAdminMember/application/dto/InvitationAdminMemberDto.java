package com.motiolab.nabusi_server.invitationAdminMember.application.dto;

import com.motiolab.nabusi_server.invitationAdminMember.domain.InvitationAdminMemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InvitationAdminMemberDto {
    private Long id;
    private String mobile;
    private Long centerId;
    private Boolean isAccept;
    private Long sendAdminMemberId;
    private Long roleId;
    private String code;

    public static InvitationAdminMemberDto from(InvitationAdminMemberEntity invitationAdminMemberEntity) {
        return InvitationAdminMemberDto.builder()
                .id(invitationAdminMemberEntity.getId())
                .mobile(invitationAdminMemberEntity.getMobile())
                .centerId(invitationAdminMemberEntity.getCenterId())
                .isAccept(invitationAdminMemberEntity.getIsAccept())
                .sendAdminMemberId(invitationAdminMemberEntity.getSendAdminMemberId())
                .roleId(invitationAdminMemberEntity.getRoleId())
                .code(invitationAdminMemberEntity.getCode())
                .build();
    }
}
