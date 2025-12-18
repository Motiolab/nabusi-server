package com.motiolab.nabusi_server.centerPackage.center.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CenterRoomRepository extends JpaRepository<CenterRoomEntity, Long> {
    List<CenterRoomEntity> findByCenterId(Long centerId);
    Integer deleteByCenterId(Long centerId);
    Optional<CenterRoomEntity> findByCenterIdAndName(Long centerId, String name);
    List<CenterRoomEntity> findAllByIdIn(List<Long> idList);
}
