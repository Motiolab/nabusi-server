package com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface WellnessTicketIssuanceRepository extends JpaRepository<WellnessTicketIssuanceEntity, Long>{
    List<WellnessTicketIssuanceEntity> findAllByWellnessTicketId(Long wellnessTicketId);
    List<WellnessTicketIssuanceEntity> findAllByMemberIdInAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(List<Long> memberIdList, ZonedDateTime startDate, ZonedDateTime expireDate);
    List<WellnessTicketIssuanceEntity> findAllByMemberIdAndStartDateBeforeAndExpireDateAfterAndIsDeleteFalse(Long memberId, ZonedDateTime startDate, ZonedDateTime expireDate);
    List<WellnessTicketIssuanceEntity> findAllByMemberId(Long memberId);
    List<WellnessTicketIssuanceEntity> findAllByIdIn(List<Long> idList);
}
