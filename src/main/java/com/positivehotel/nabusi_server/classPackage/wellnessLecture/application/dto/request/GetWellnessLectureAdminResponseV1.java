package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class GetWellnessLectureAdminResponseV1 {
    private Long id;
    private String name;
    private Integer maxReservationCnt;
    private String room;
    private Long teacherId;
    private String teacherName;
    private Long wellnessLectureTypeId;
    private String wellnessLectureTypeName;
    private String wellnessLectureTypeDescription;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Boolean isDelete;
}
