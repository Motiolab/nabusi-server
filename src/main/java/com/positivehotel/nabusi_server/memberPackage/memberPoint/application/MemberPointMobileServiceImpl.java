package com.positivehotel.nabusi_server.memberPackage.memberPoint.application;

import com.positivehotel.nabusi_server.memberPackage.memberPoint.application.dto.MemberPointDto;
import com.positivehotel.nabusi_server.memberPackage.memberPoint.application.dto.request.UpdateMemberPointMobileRequestV1;
import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.application.MemberPointHistoryService;
import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.application.dto.MemberPointHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPointMobileServiceImpl implements MemberPointMobileService {
    private final MemberPointService memberPointService;
    private final MemberPointHistoryService memberPointHistoryService;

    @Override
    public void updateMemberPoint(UpdateMemberPointMobileRequestV1 updateMemberPointMobileRequestV1) {
        memberPointService.addPoint(updateMemberPointMobileRequestV1.getMemberId(), updateMemberPointMobileRequestV1.getAmount());
        memberPointHistoryService.create(MemberPointHistoryDto.builder()
                .memberId(updateMemberPointMobileRequestV1.getMemberId())
                .amount(updateMemberPointMobileRequestV1.getAmount())
                .transactionType(updateMemberPointMobileRequestV1.getTransactionType())
                .description(updateMemberPointMobileRequestV1.getDescription())
                .referenceId(updateMemberPointMobileRequestV1.getReferenceId())
                .build()
        );
    }

    @Override
    public Long getMemberPointByMemberId(Long memberId) {
        final MemberPointDto memberPointDto = memberPointService.getByMemberId(memberId);
        return memberPointDto == null ? 0L : memberPointDto.getPoint();
    }
}
