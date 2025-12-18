package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.response;

import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetWellnessLectureDetailByWellnessLectureIdResponse {
    private Long id;
    private List<String> lectureImageUrlList;
    private Long teacherId;
    private String teacherName;
    private String teacherNickName;
    private String teacherImageUrl;
    private String teacherIntroduce;
    private Double rating;
    private Integer reviewCnt;
    private String name;
    private Boolean isFullBooking;
    private String wellnessLectureTypeName;
    private String wellnessLectureTypeDescription;
    private String description;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Long centerId;
    private String centerName;
    private String centerAddress;
    private String centerDetailAddress;
    private String room;
    private Double price;
    private Boolean isReserved;
    private ReservationStatus reservationStatus;
    private Long reservationId;
    private Long reservedWellnessTicketIssuanceId;
    private List<String> centerContactNumber;
    private List<WellnessTicketIssuanceAvailable> wellnessTicketIssuanceAvailableList;

    @Builder
    @Getter
    public static class WellnessTicketIssuanceAvailable {
        private Long id;
        private String name;
        private ZonedDateTime startDate;
        private ZonedDateTime expireDate;
        private Integer remainingCnt;
        private Long remainingDate;
    }
}
