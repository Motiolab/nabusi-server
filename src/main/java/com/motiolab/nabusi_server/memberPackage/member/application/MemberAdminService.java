package com.motiolab.nabusi_server.memberPackage.member.application;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberAdminDto;

import java.util.List;

public interface MemberAdminService {
    MemberAdminDto getMemberById(Long memberId);
    List<MemberAdminDto> getMemberListToAddTeacherByCenterId(Long centerId);
    List<MemberAdminDto> getAdminMemberListByCenterId(Long centerId);
    Boolean updateMemberRole(Long centerId, Long memberId, Long roleId);
    Boolean deleteAdminRoleByMemberIdList(Long centerId, List<Long> memberIdList);
    Boolean passOwnerRole(Long memberId, Long centerId, Long targetMemberId);
    List<MemberAdminDto> getAllMemberByCenterId(Long centerId);
    List<MemberAdminDto> getAllMemberListByCenterId(Long centerId);
    MemberAdminDto getMemberDetailById(Long id);
}
