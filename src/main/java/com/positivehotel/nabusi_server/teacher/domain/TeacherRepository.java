package com.positivehotel.nabusi_server.teacher.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    List<TeacherEntity> findAllByIdIn(List<Long> idList);
    Optional<TeacherEntity> findByCenterIdAndMemberId(Long centerId, Long memberId);
    List<TeacherEntity> findAllByCenterId(Long centerId);
}
