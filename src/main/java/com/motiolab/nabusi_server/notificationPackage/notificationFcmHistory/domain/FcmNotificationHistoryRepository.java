package com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FcmNotificationHistoryRepository extends JpaRepository<FcmNotificationHistoryEntity, Long> {
    List<FcmNotificationHistoryEntity> findAllByMemberIdOrderByCreatedDateDesc(Long memberId);
}
