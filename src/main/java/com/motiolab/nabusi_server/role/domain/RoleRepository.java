package com.motiolab.nabusi_server.role.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    List<RoleEntity> findAllByIdIn(List<Long> idList);
    List<RoleEntity> findAllByCenterId(Long centerId);
    Optional<RoleEntity> findAllByCenterIdAndName(Long centerId, String name);
    Optional<RoleEntity> findByNameAndCenterId(String name, Long centerId);
}
