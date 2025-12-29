package com.motiolab.nabusi_server.memberPackage.member.application;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.GetHomeSummaryMobileResponse;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.GetMemberMyInfoMobileResponse;

public interface MemberMobileService {
    Boolean delete(Long memberId);

    GetMemberMyInfoMobileResponse getMyInfo(Long memberId);

    GetHomeSummaryMobileResponse getHomeSummary(Long memberId);
}
