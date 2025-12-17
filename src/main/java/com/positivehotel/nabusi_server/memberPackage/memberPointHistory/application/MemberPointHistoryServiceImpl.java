package com.positivehotel.nabusi_server.memberPackage.memberPointHistory.application;

import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.application.dto.MemberPointHistoryDto;
import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.domain.MemberPointHistoryEntity;
import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.domain.MemberPointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPointHistoryServiceImpl implements MemberPointHistoryService {
    private final MemberPointHistoryRepository memberPointHistoryRepository;

    @Override
    public void create(MemberPointHistoryDto memberPointHistoryDto) {
        memberPointHistoryRepository.save(MemberPointHistoryEntity.create(
                memberPointHistoryDto.getMemberId(),
                memberPointHistoryDto.getTransactionType(),
                memberPointHistoryDto.getAmount(),
                memberPointHistoryDto.getDescription(),
                memberPointHistoryDto.getReferenceId()
        ));
    }

    @Override
    public List<MemberPointHistoryDto> getAllByMemberId(Long memberId) {
        return memberPointHistoryRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberPointHistoryDto::from)
                .toList();
    }
}
