package com.motiolab.nabusi_server.ticketPackage.wellnessTicketExtension.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WellnessTicketExtensionRepository extends JpaRepository<WellnessTicketExtensionEntity, Long> {
    List<WellnessTicketExtensionEntity> findAllByWellnessTicketId(Long wellnessTicketId);
}
