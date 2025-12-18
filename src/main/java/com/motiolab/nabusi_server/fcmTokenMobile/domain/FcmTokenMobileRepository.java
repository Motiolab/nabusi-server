package com.motiolab.nabusi_server.fcmTokenMobile.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenMobileRepository extends JpaRepository<FcmTokenMobileEntity, Long> {
    Optional<FcmTokenMobileEntity> findByMemberId(Long memberId);
}
