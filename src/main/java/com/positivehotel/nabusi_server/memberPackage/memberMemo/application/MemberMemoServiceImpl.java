package com.positivehotel.nabusi_server.memberPackage.memberMemo.application;

import com.positivehotel.nabusi_server.memberPackage.memberMemo.application.dto.MemberMemoDto;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.domain.MemberMemoEntity;
import com.positivehotel.nabusi_server.memberPackage.memberMemo.domain.MemberMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberMemoServiceImpl implements MemberMemoService {
    private final MemberMemoRepository memberMemoRepository;


    @Override
    public List<MemberMemoDto> getAllByMemberIdList(List<Long> memberIdList) {
        final List<MemberMemoEntity> memberMemoEntityList = memberMemoRepository.findAllByMemberIdIn(memberIdList);
        return memberMemoEntityList.stream().map(MemberMemoDto::from).toList();
    }

    @Override
    public List<MemberMemoDto> getAllByMemberId(Long memberId) {
        final List<MemberMemoEntity> memberMemoEntityList = memberMemoRepository.findAllByMemberId(memberId);
        return memberMemoEntityList.stream().map(MemberMemoDto::from).toList();
    }

    @Override
    public void create(MemberMemoDto memberMemoDto) {
        final MemberMemoEntity memberMemoEntity = MemberMemoEntity.create(
                memberMemoDto.getContent(),
                memberMemoDto.getMemberId(),
                memberMemoDto.getRegisterId()
        );
        memberMemoRepository.save(memberMemoEntity);
    }
}
