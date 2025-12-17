package com.positivehotel.nabusi_server.socialUser.kakaoUser.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface KakaoUserRepository extends JpaRepository<KakaoUserEntity, Long> {
    Optional<KakaoUserEntity> findByPhoneNumber(@NonNull String phoneNumber);
    Optional<KakaoUserEntity> findByMemberId(Long memberId);
}
