package com.positivehotel.nabusi_server.memberPackage.memberMemo.application;

import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.dto.request.CreateMemberMemoAdminRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMemoAdminServiceImpl implements MemberMemoAdminService{
    private final MemberMemoService memberMemoService;

    @Override
    public void createMemberMemo(CreateMemberMemoAdminRequestV1 createMemberMemoAdminRequestV1) {
        final MemberMemoDto memberMemoDto = MemberMemoDto.builder()
                .content(createMemberMemoAdminRequestV1.getContent())
                .memberId(createMemberMemoAdminRequestV1.getMemberId())
                .registerId(createMemberMemoAdminRequestV1.getRegisterId())
                .build();

        memberMemoService.create(memberMemoDto);
    }
}
