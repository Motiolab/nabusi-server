package com.motiolab.nabusi_server.reservation.application;

import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;

import java.util.List;

public interface ReservationService {
    ReservationDto create(ReservationDto reservationDto);

    List<ReservationDto> getAllByWellnessLectureId(Long wellnessLectureId);

    List<ReservationDto> getAllByWellnessTicketIssuanceId(Long wellnessTicketIssuanceId);

    List<ReservationDto> getAllByWellnessLectureIdList(List<Long> wellnessLectureIdList);

    ReservationDto getById(Long id);

    ReservationDto update(ReservationDto reservationDto);

    List<ReservationDto> getAllByMemberIdAndStatusList(Long memberId, List<ReservationStatus> reservationStatusList);

    ReservationDto getByMemberIdAndWellnessLectureId(Long memberId, Long wellnessLectureId);

    List<ReservationDto> getAllByMemberIdListAndStatusList(List<Long> memberIdList,
            List<ReservationStatus> reservationStatusList);

    long countByMemberIdAndStatus(Long memberId, ReservationStatus status);

    long countByMemberIdAndStatusIn(Long memberId, List<ReservationStatus> statusList);
}
