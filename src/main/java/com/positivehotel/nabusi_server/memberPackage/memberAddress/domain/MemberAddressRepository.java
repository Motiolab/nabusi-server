package com.positivehotel.nabusi_server.memberPackage.memberAddress.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAddressRepository extends JpaRepository<MemberAddressEntity, Long> {
    List<MemberAddressEntity> findAllByMemberId(Long memberId);
}
