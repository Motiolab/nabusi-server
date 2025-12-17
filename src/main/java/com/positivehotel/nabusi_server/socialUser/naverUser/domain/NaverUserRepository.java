package com.positivehotel.nabusi_server.socialUser.naverUser.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface NaverUserRepository extends JpaRepository<NaverUserEntity, Long> {
    Optional<NaverUserEntity> findByMobileE164(@NonNull String mobileE164);
    Optional<NaverUserEntity> findByMemberId(Long memberId);
}
