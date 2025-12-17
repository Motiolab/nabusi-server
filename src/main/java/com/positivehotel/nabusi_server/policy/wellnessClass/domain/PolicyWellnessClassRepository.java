package com.positivehotel.nabusi_server.policy.wellnessClass.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyWellnessClassRepository extends JpaRepository<PolicyWellnessClassEntity, Long> {
    Optional<PolicyWellnessClassEntity> findByCenterId(Long centerId);
}
