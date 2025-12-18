package com.motiolab.nabusi_server.reservation.domain;

import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAllByWellnessLectureId(Long wellnessLectureId);
    List<ReservationEntity> findAllByWellnessTicketIssuanceId(Long wellnessTicketIssuanceId);
    List<ReservationEntity> findAllByWellnessLectureIdIn(List<Long> wellnessLectureIdList);
    List<ReservationEntity> findAllByMemberIdAndStatusIn(Long memberId, List<ReservationStatus> reservationStatusList);
    Optional<ReservationEntity> findByMemberIdAndWellnessLectureId(Long memberId, Long wellnessLectureId);
    List<ReservationEntity> findAllByMemberIdInAndStatusIn(List<Long> memberIdList, List<ReservationStatus> reservationStatusList);
}
