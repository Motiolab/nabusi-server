package com.positivehotel.nabusi_server.mobileAuthCode.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MobileAuthCodeRepository extends JpaRepository<MobileAuthCodeEntity, Long> {
    Optional<MobileAuthCodeEntity> findByAuthCode(String authCode);
}
