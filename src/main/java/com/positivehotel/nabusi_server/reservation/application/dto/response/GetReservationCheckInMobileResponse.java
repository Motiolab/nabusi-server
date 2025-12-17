package com.positivehotel.nabusi_server.reservation.application.dto.response;

import com.positivehotel.nabusi_server.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetReservationCheckInMobileResponse {
    private Long reservationId;
    private Long lectureId;
    private String lectureImageUrl;
    private String lectureTypeName;
    private String lectureTypeDescription;
    private String lectureName;
    private ReservationStatus reservationStatus;
    private ZonedDateTime lectureStartDateTime;
    private ZonedDateTime lectureEndDateTime;
    private String teacherName;
    private String lectureRoom;
    private String centerName;
    private Long lectureReviewId;
    private Double rating;
    private Integer reviewCnt;
}
