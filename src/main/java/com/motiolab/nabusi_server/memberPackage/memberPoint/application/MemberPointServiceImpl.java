package com.motiolab.nabusi_server.memberPackage.memberPoint.application;

import com.motiolab.nabusi_server.memberPackage.memberPoint.application.dto.MemberPointDto;
import com.motiolab.nabusi_server.memberPackage.memberPoint.domain.MemberPointEntity;
import com.motiolab.nabusi_server.memberPackage.memberPoint.domain.MemberPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPointServiceImpl implements MemberPointService {
    private final MemberPointRepository memberPointRepository;

    @Override
    public void addPoint(Long memberId, Long point) {
        memberPointRepository.findByMemberId(memberId).ifPresentOrElse(
                memberPointEntity -> memberPointEntity.addPoint(point),
                () -> memberPointRepository.save(MemberPointEntity.create(memberId, point))
        );
    }

    @Override
    public MemberPointDto getByMemberId(Long memberId) {
        return memberPointRepository.findByMemberId(memberId)
                .map(MemberPointDto::from)
                .orElse(null);
    }
}
