package com.positivehotel.nabusi_server.memberPackage.member.application.dto.request;

public record GetMemberToAddTeacherAdminResponseV1(
        Long memberId,
        String name,
        String mobile,
        String roleName
) {
}
