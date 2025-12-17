package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class TimeWithDay {
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    Integer dayOfWeek;
}
