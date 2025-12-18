package com.motiolab.nabusi_server.memberPackage.memberPointHistory.application;

import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.MemberPointHistoryDto;

import java.util.List;

public interface MemberPointHistoryService {
    void create(MemberPointHistoryDto memberPointHistoryDto);
    List<MemberPointHistoryDto> getAllByMemberId(Long memberId);
}
