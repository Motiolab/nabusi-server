package com.motiolab.nabusi_server.urlPattern.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlPatternRepository extends JpaRepository<UrlPatternEntity, Long> {
    List<UrlPatternEntity> findAllByCenterIdAndActionName(Long centerId, String actionName);
    List<UrlPatternEntity> findAllByCenterId(Long centerId);
}
