package com.positivehotel.nabusi_server.memberPackage.memberPoint.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPointRepository extends JpaRepository<MemberPointEntity, Long> {
    Optional<MemberPointEntity> findByMemberId(Long memberId);
}
