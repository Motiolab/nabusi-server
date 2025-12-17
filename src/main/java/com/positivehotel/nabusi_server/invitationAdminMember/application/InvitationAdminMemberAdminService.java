package com.positivehotel.nabusi_server.invitationAdminMember.application;

import com.positivehotel.nabusi_server.invitationAdminMember.application.dto.InvitationAdminMemberAdminDto;
import com.positivehotel.nabusi_server.invitationAdminMember.application.dto.request.InviteAdminMemberRequestV1;

public interface InvitationAdminMemberAdminService {
    void inviteAdminMemberByCenterId(Long centerId, Long sendAdminMemberId, InviteAdminMemberRequestV1 inviteAdminMemberRequestV1);
    InvitationAdminMemberAdminDto getInviteAdminMemberToMe(Long memberId, String code);
    void acceptInviteAdminMember(Long memberId, Long invitationAdminMemberId);
}
