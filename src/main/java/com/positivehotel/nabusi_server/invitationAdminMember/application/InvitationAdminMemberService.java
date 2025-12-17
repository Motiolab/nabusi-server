package com.positivehotel.nabusi_server.invitationAdminMember.application;

import com.positivehotel.nabusi_server.invitationAdminMember.application.dto.InvitationAdminMemberDto;

public interface InvitationAdminMemberService {
    void create(InvitationAdminMemberDto invitationAdminMemberDto);
    InvitationAdminMemberDto getByCenterIdAndMobileAndIsAcceptFalse(Long centerId, String mobile);
    void update(InvitationAdminMemberDto invitationAdminMemberDto);
    InvitationAdminMemberDto getByMobileAndCode(String mobile, String code);
    InvitationAdminMemberDto getById(Long id);
}
