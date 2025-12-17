package com.positivehotel.nabusi_server.memberPackage.memberPointHistory.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberPointHistoryRepository extends JpaRepository<MemberPointHistoryEntity, Long> {
    List<MemberPointHistoryEntity> findAllByMemberId(Long memberId);
}
