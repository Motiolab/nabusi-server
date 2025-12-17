package com.positivehotel.nabusi_server.classPackage.wellnessLectureType.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WellnessLectureTypeRepository extends JpaRepository<WellnessLectureTypeEntity, Long> {
    List<WellnessLectureTypeEntity> findAllByCenterId(Long centerId);
    Optional<WellnessLectureTypeEntity> findByCenterIdAndName(Long centerId, String name);
}
