package com.positivehotel.nabusi_server.socialUser.appleUser.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AppleUserRepository extends JpaRepository<AppleUserEntity, Long> {
    Optional<AppleUserEntity> findByMobile(@NonNull String mobile);
    Optional<AppleUserEntity> findBySub(@NonNull String sub);
    Optional<AppleUserEntity> findByMemberId(@NonNull Long memberId);
}
