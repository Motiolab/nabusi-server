package com.positivehotel.nabusi_server.memberPackage.memberMemo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberMemoRepository extends JpaRepository<MemberMemoEntity, Long> {
    List<MemberMemoEntity> findAllByMemberIdIn(List<Long> memberIdList);
    List<MemberMemoEntity> findAllByMemberId(Long memberId);
}
