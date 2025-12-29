package com.motiolab.nabusi_server.memberPackage.member.application;

import com.motiolab.nabusi_server.memberPackage.member.application.dto.response.GetHomeSummaryMobileResponse;

public interface MemberMobileService {
    Boolean delete(Long memberId);

    GetHomeSummaryMobileResponse getHomeSummary(Long memberId);
}
