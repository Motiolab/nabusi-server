package com.motiolab.nabusi_server.teacher.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    @Query("SELECT t FROM TeacherEntity t JOIN MemberEntity m ON t.memberId = m.id WHERE t.id IN :idList AND m.isDelete = false")
    List<TeacherEntity> findAllByIdInAndMemberIsDeleteFalse(List<Long> idList);

    Optional<TeacherEntity> findByCenterIdAndMemberId(Long centerId, Long memberId);

    @Query("SELECT t FROM TeacherEntity t JOIN MemberEntity m ON t.memberId = m.id WHERE t.centerId = :centerId AND m.isDelete = false")
    List<TeacherEntity> findAllByCenterIdAndMemberIsDeleteFalse(Long centerId);

    List<TeacherEntity> findAllByMemberId(Long memberId);
}
