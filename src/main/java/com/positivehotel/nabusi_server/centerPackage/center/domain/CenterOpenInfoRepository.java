package com.positivehotel.nabusi_server.centerPackage.center.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterOpenInfoRepository extends JpaRepository<CenterOpenInfoEntity, Long> {
    List<CenterOpenInfoEntity> findByCenterId(Long centerId);
    Integer deleteByCenterId(Long centerId);
}
