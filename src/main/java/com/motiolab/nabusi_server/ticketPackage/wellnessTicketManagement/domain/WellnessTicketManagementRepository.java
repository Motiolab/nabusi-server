package com.motiolab.nabusi_server.ticketPackage.wellnessTicketManagement.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WellnessTicketManagementRepository extends JpaRepository<WellnessTicketManagementEntity, Long>{
    List<WellnessTicketManagementEntity> findAllByWellnessTicketId(Long wellnessTicketId);
    void deleteAllByIdIn(List<Long> idList);
    Optional<WellnessTicketManagementEntity> findByWellnessTicketIdAndWellnessTicketIssuanceName(Long wellnessTicketId, String wellnessTicketIssuanceName);
    List<WellnessTicketManagementEntity> findAllByCenterId(Long centerId);
    Optional<WellnessTicketManagementEntity> findByCenterIdAndWellnessTicketIdAndWellnessTicketIssuanceName(Long centerId, Long wellnessTicketId, String wellnessTicketIssuanceName);
    @Query(value = "SELECT wtm.* FROM wellness_ticket_management wtm WHERE FIND_IN_SET(:wellnessTicketIssuanceId, wtm.wellness_ticket_issuance_id_list)", nativeQuery = true)
    List<WellnessTicketManagementEntity> findByWellnessTicketIssuanceId(Long wellnessTicketIssuanceId);

}
