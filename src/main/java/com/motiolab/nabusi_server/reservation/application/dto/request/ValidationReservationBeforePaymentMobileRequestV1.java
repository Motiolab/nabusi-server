package com.motiolab.nabusi_server.reservation.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ValidationReservationBeforePaymentMobileRequestV1 {
    private Long memberId;
    private Long wellnessLectureId;
}
