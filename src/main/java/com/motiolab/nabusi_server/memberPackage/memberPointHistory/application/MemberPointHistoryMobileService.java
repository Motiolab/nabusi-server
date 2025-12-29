package com.motiolab.nabusi_server.memberPackage.memberPointHistory.application;

import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.response.GetMemberPointHistoryMobileResponse;

import java.util.List;

public interface MemberPointHistoryMobileService {
    List<GetMemberPointHistoryMobileResponse> getPointHistory(Long memberId);
}
