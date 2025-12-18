package com.motiolab.nabusi_server.ticketPackage.wellnessTicket.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WellnessTicketRepository extends JpaRepository<WellnessTicketEntity, Long> {
     List<WellnessTicketEntity> findAllByCenterId(Long centerId);
     Optional<WellnessTicketEntity> findByNameAndCenterId(String name, Long centerId);
}
