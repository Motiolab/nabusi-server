package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class CreateWellnessLectureListWithWellnessClassAdminRequestV1 {
    Long wellnessClassId;
    String name;
    String description;
    Long centerId;
    Integer maxReservationCnt;
    Long registrantId;
    String room;
    List<String> classImageUrlList;
    Double price;
    Long teacherId;
    Long wellnessLectureTypeId;
    ZonedDateTime startDateTime;
    ZonedDateTime endDateTime;
    List<TimeRangeWithDay> timeRangeWithDays;
    List<Long> wellnessTicketManagementIdList;

    @Value
    public static class TimeRangeWithDay {
        ZonedDateTime startTime;
        ZonedDateTime endTime;
        Integer dayOfWeek;
    }
}
