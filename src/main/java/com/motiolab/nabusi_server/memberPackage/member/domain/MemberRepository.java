package com.motiolab.nabusi_server.memberPackage.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMobileAndSocialName(String mobile, String socialName);
    @Query(value = "SELECT m.* FROM member m WHERE FIND_IN_SET(:centerId, m.center_id_list)", nativeQuery = true)
    List<MemberEntity> findMembersByCenterId(Long centerId);
    List<MemberEntity> findAllByRolesId(Long roleId);
    @Query(value = "SELECT m.* FROM member m WHERE FIND_IN_SET(:centerId, m.center_id_list) and m.id = :id", nativeQuery = true)
    List<MemberEntity> findAllByCenterIdAndId(Long centerId, Long id);
}
