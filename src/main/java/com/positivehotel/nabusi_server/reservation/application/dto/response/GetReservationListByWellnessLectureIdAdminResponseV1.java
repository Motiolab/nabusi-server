package com.positivehotel.nabusi_server.reservation.application.dto.response;

import com.positivehotel.nabusi_server.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetReservationListByWellnessLectureIdAdminResponseV1 {
    private Long reservationId;
    private Long memberId;
    private String memberName;
    private String memberMobile;
    private String memberMemo;
    private String wellnessTicketIssuanceName;
    private String wellnessTicketIssuanceBackgroundColor;
    private String wellnessTicketIssuanceType;
    private String wellnessTicketIssuanceTextColor;
    private ReservationStatus reservationStatus;
    private ZonedDateTime reservationCreatedDate;
}
