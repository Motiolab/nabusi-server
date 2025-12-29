package com.motiolab.nabusi_server.memberPackage.memberPointHistory.application;

import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.MemberPointHistoryDto;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.response.GetMemberPointHistoryMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPointHistoryMobileServiceImpl implements MemberPointHistoryMobileService {
    private final MemberPointHistoryService memberPointHistoryService;

    @Override
    public List<GetMemberPointHistoryMobileResponse> getPointHistory(Long memberId) {
        List<MemberPointHistoryDto> historyList = memberPointHistoryService.getAllByMemberId(memberId);

        return historyList.stream()
                .map(dto -> GetMemberPointHistoryMobileResponse.builder()
                        .id(dto.getId())
                        .transactionType(dto.getTransactionType().toString())
                        .amount(dto.getAmount())
                        .description(dto.getDescription())
                        .referenceId(dto.getReferenceId())
                        .createdDate(dto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build())
                .toList();
    }
}
