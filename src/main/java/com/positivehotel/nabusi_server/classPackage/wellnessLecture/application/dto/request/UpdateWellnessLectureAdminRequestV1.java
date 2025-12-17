package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class UpdateWellnessLectureAdminRequestV1 {
    Long id;
    String name;
    String description;
    Long centerId;
    Integer maxReservationCnt;
    String room;
    List<String> lectureImageUrlList;;
    Long teacherId;
    Long wellnessLectureTypeId;
    ZonedDateTime startDateTime;
    ZonedDateTime endDateTime;
    List<Long> wellnessTicketManagementIdList;
    Double price;
}
