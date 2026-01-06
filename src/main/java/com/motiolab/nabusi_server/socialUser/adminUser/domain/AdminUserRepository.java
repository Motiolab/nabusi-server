package com.motiolab.nabusi_server.socialUser.adminUser.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {
    Optional<AdminUserEntity> findByEmail(String email);

    Optional<AdminUserEntity> findByMemberId(Long memberId);
}
