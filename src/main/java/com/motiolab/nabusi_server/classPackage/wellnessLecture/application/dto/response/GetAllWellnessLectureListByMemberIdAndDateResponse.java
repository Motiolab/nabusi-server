package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetAllWellnessLectureListByMemberIdAndDateResponse {
    private Long id;
    private String lectureImageUrl;
    private String status;
    private String name;
    private String teacherName;
    private String room;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Boolean isFullBooking;
    private String wellnessLectureTypeName;
    private String wellnessLectureTypeDescription;
    private Boolean isCreatedReview;
    private List<Reservation> reservation;

    @Builder
    @Getter
    public static class Reservation {
        private Long id;
        private Long centerId;
        private Long memberId;
        private String memberName;
        private String memberMobile;
        private Long paymentId;
        private Long actionMemberId;
        private String status;
        private Long wellnessLectureId;
        private WellnessTicketIssuance wellnessTicketIssuance;

        @Builder
        @Getter
        public static class WellnessTicketIssuance {
            private Long id;
            private String name;
            private ZonedDateTime startDate;
            private ZonedDateTime expireDate;
            private String type;
            private String backgroundColor;
            private String textColor;
            private String limitType;
            private Integer limitCnt;
            private Integer remainingCnt;
            private Integer totalUsableCnt;
        }
    }
}