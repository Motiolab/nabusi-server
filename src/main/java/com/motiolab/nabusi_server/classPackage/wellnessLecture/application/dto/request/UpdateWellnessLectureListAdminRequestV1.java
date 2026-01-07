package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class UpdateWellnessLectureListAdminRequestV1 {
    List<Long> idList;
    String name;
    String description;
    Long centerId;
    Integer maxReservationCnt;
    String room;
    List<String> lectureImageUrlList;
    Long teacherId;
    Long wellnessLectureTypeId;
    List<Long> wellnessTicketManagementIdList;
    Double price;
}
