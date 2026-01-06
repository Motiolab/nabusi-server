package com.motiolab.nabusi_server.memberPackage.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMobileAndSocialNameAndIsDeleteFalse(String mobile, String socialName);

    @Query(value = "SELECT m.* FROM member m WHERE FIND_IN_SET(:centerId, m.center_id_list) AND m.is_delete = 0", nativeQuery = true)
    List<MemberEntity> findMembersByCenterId(Long centerId);

    List<MemberEntity> findAllByRolesIdAndIsDeleteFalse(Long roleId);

    @Query(value = "SELECT m.* FROM member m WHERE FIND_IN_SET(:centerId, m.center_id_list) AND m.id = :id AND m.is_delete = 0", nativeQuery = true)
    List<MemberEntity> findAllByCenterIdAndId(Long centerId, Long id);

    List<MemberEntity> findAllByIsDeleteFalse();

    List<MemberEntity> findAllByIdInAndIsDeleteFalse(List<Long> idList);
}
