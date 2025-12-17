package com.positivehotel.nabusi_server.reservation.application;

import com.positivehotel.nabusi_server.reservation.application.dto.request.CancelReservationMobileRequestV1;
import com.positivehotel.nabusi_server.reservation.application.dto.request.CreateReservationMobileRequestV1;
import com.positivehotel.nabusi_server.reservation.application.dto.response.ReservationMobileDto;

import java.util.List;

public interface ReservationMobileService {
    void createReservation(CreateReservationMobileRequestV1 createReservationMobileRequestV1);
    void cancelReservation(CancelReservationMobileRequestV1 cancelReservationMobileRequestV1);
    List<ReservationMobileDto> getReservationList(Long memberId);
    List<ReservationMobileDto> getReservationCheckInList(Long memberId);
}
