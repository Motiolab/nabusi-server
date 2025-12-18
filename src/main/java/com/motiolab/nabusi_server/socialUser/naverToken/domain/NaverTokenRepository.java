package com.motiolab.nabusi_server.socialUser.naverToken.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NaverTokenRepository extends JpaRepository<NaverTokenEntity, Long> {
    Optional<NaverTokenEntity> findByMemberIdAndAccessToken(Long memberId, String accessToken);
    Optional<NaverTokenEntity> findByMemberId(Long memberId);
    Optional<NaverTokenEntity> findFirstByMemberIdOrderByCreatedDateDesc(Long memberId);
}
