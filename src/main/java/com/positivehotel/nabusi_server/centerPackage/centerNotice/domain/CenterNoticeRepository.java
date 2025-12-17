package com.positivehotel.nabusi_server.centerPackage.centerNotice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterNoticeRepository extends JpaRepository<CenterNoticeEntity, Long> {
    List<CenterNoticeEntity> findAllByCenterId(Long centerId);

}
