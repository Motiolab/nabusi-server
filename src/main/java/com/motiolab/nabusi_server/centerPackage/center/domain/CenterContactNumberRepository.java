package com.motiolab.nabusi_server.centerPackage.center.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterContactNumberRepository extends JpaRepository<CenterContactNumberEntity, Long> {
    List<CenterContactNumberEntity> findByCenterId(Long centerId);
    Integer deleteByCenterId(Long centerId);
}
