package com.positivehotel.nabusi_server.reservation.application.dto.request;

import com.positivehotel.nabusi_server.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateReservationMobileRequestV1 {
    private Long centerId;
    private Long memberId;
    private Long actionMemberId;
    private ReservationStatus status;
    private Long wellnessLectureId;
    private Long wellnessTicketIssuanceId;
}
