package com.positivehotel.nabusi_server.classPackage.wellnessLecture.application;


import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.CalcZoneDateTimeListResponse;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.RangeZoneDateTime;
import com.positivehotel.nabusi_server.classPackage.wellnessLecture.application.dto.TimeWithDay;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Log
public class CalcZoneDateTime {
    public List<CalcZoneDateTimeListResponse> getByRangeDate(final RangeZoneDateTime rangeDate, final List<TimeWithDay> timeWithDayList) {
        final List<CalcZoneDateTimeListResponse> returnValue = new ArrayList<>();
        final ZonedDateTime startDateTime = rangeDate.getStartDateTime();
        final ZonedDateTime endDateTime = rangeDate.getEndDateTime();

        log.info("[CalcDate] *getByRangeDate rangeDate: " + startDateTime + " / " + endDateTime);

        final List<TimeWithDay> sortedTimeWithDayList = timeWithDayList.stream()
                .map(timeWithDay -> TimeWithDay.builder()
                        .startTime(timeWithDay.getStartTime().withZoneSameInstant(ZoneId.of("Asia/Seoul")))
                        .endTime(timeWithDay.getEndTime().withZoneSameInstant(ZoneId.of("Asia/Seoul")))
                        .dayOfWeek(timeWithDay.getDayOfWeek())
                        .build())
                .sorted(Comparator.comparingInt(TimeWithDay::getDayOfWeek))
                .toList();

        ZonedDateTime currentDate = startDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        while (!currentDate.isAfter(endDateTime)) {
            for (TimeWithDay timeWithDay : sortedTimeWithDayList) {
                currentDate = currentDate
                        .with(ChronoField.DAY_OF_WEEK, timeWithDay.getDayOfWeek())
                        .withHour(timeWithDay.getStartTime().getHour())
                        .withMinute(timeWithDay.getStartTime().getMinute());

                if (!currentDate.isBefore(startDateTime) && !currentDate.isAfter(endDateTime)) {
                    final ZonedDateTime end = currentDate
                            .plusSeconds(Duration.between(timeWithDay.getStartTime(), timeWithDay.getEndTime()).getSeconds());

                    returnValue.add(CalcZoneDateTimeListResponse.builder()
                            .startDateTime(currentDate.withZoneSameInstant(ZoneId.of("UTC")))
                            .endDateTime(end.withZoneSameInstant(ZoneId.of("UTC")))
                            .build());
                }

                if (!currentDate.isBefore(endDateTime)) {
                    break;
                }
            }
            currentDate = currentDate.plusWeeks(1);
        }

        return returnValue;
    }

}
