package com.motiolab.nabusi_server.classPackage.wellnessClass.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WellnessClassRepository extends JpaRepository<WellnessClassEntity, Long> {
    List<WellnessClassEntity> findAllByCenterId(Long centerId);
    Optional<WellnessClassEntity> findByCenterIdAndName(Long centerId, String name);
    Optional<WellnessClassEntity> findByIdAndCenterId(Long id, Long centerId);

    @Query(value = "SELECT * FROM wellness_class " +
            "WHERE wellness_ticket_management_id_list REGEXP CONCAT('(^|,)', :wellnessTicketManagementIdList, '(,|$)')",
            nativeQuery = true)
    List<WellnessClassEntity> findAllByIssuedWellnessTicketManageIdListFindInSet(String wellnessTicketManagementIdList);

}
