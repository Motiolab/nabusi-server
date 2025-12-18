package com.motiolab.nabusi_server.memberPackage.memberPoint.application;

import com.motiolab.nabusi_server.memberPackage.memberPoint.application.dto.request.UpdateMemberPointMobileRequestV1;

public interface MemberPointMobileService {
    void updateMemberPoint(UpdateMemberPointMobileRequestV1 updateMemberPointMobileRequestV1);
    Long getMemberPointByMemberId(Long memberId);
}
