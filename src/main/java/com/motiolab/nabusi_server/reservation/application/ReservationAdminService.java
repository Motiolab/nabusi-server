package com.motiolab.nabusi_server.reservation.application;

import com.motiolab.nabusi_server.reservation.application.dto.ReservationAdminDto;
import com.motiolab.nabusi_server.reservation.application.dto.request.CreateReservationAdminRequestV1;

import java.util.List;

public interface ReservationAdminService {
    void createReservation(CreateReservationAdminRequestV1 createReservationAdminRequestV1);

    List<ReservationAdminDto> getReservationListByWellnessLectureId(Long wellnessLectureId);

    void cancelReservation(Long reservationId, Long actionMemberId);
}
