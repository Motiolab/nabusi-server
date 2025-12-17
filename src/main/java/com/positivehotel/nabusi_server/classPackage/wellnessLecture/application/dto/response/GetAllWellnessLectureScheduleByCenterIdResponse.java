package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetAllWellnessLectureScheduleByCenterIdResponse {
    private ZonedDateTime initSelectedDate;
    private List<String> holidays;
    private List<ZonedDateTime> eventDays;
    private List<ZonedDateTime> activeDays;
}
