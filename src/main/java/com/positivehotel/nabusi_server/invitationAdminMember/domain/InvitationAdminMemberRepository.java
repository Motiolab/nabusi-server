package com.positivehotel.nabusi_server.invitationAdminMember.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationAdminMemberRepository extends JpaRepository<InvitationAdminMemberEntity, Long> {
    Optional<InvitationAdminMemberEntity> findByCenterIdAndMobileAndIsAcceptFalse(Long centerId, String mobile);
    Optional<InvitationAdminMemberEntity> findByMobileAndCodeAndIsAcceptFalse(String mobile, String code);
}
