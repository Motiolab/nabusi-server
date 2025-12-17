package com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto.response;

import com.positivehotel.nabusi_server.reservation.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetWellnessLectureManageByDateResponseV1 {
    private Long id;
    private String name;
    private Long centerId;
    private Integer maxReservationCnt;
    private String room;
    private String lectureImageUrl;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Boolean isDelete;
    private String teacherName;
    private List<Reservation> reservationList;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reservation {
        private Long id;
        private Long centerId;
        private Long memberId;
        private ReservationStatus status;
        private Long wellnessTicketIssuanceId;
        private ZonedDateTime lastUpdatedDate;
        private ZonedDateTime createdDate;
        private String wellnessIssuanceName;
        private ZonedDateTime expireDate;
        private String type;
        private Integer remainingCnt;
        private Integer totalUsableCnt;
    }
}