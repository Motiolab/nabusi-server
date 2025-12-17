package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class GetWellnessLectureDetailAdminResponseV1 {
    private Long id;
    private String name;
    private String description;
    private Long centerId;
    private Integer maxReservationCnt;
    private String room;
    private List<String> lectureImageUrlList;
    private Double price;
    private Long teacherId;
    private String teacherName;
    private Long wellnessLectureTypeId;
    private String wellnessLectureTypeName;
    private String wellnessLectureTypeDescription;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Boolean isDelete;
    private List<WellnessTicketAvailable> wellnessTicketAvailableList;

    @Builder
    @Getter
    public static class WellnessTicketAvailable {
        Long wellnessTicketManagementId;
        String wellnessTicketIssuanceName;
        Long wellnessTicketId;
        String type;
        String backgroundColor;
        String textColor;
        Boolean isDelete;
    }
}
