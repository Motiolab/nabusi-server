package com.positivehotel.nabusi_server.centerPackage.center.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CenterRepository extends JpaRepository<CenterEntity, Long> {
    @Query(value = """
            SELECT c.*
            FROM center c
            WHERE JSON_CONTAINS(c.member_id_list, CAST(:memberId AS JSON), '$');
            """, nativeQuery = true)
    List<CenterEntity> findByMemberId(Long memberId);
    List<CenterEntity> findAllByIsActiveTrue();
}
