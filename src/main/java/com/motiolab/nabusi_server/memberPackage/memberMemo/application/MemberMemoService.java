package com.motiolab.nabusi_server.memberPackage.memberMemo.application;

import com.motiolab.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;

import java.util.List;

public interface MemberMemoService {
    List<MemberMemoDto> getAllByMemberIdList(List<Long> memberIdList);
    List<MemberMemoDto> getAllByMemberId(Long memberId);
    void create(MemberMemoDto memberMemoDto);
}
