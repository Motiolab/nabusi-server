package com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetWellnessLectureBookingDateListByWellnessClassIdResponse {
    private ZonedDateTime initSelectedDate;
    private List<String> holidays;
    private List<ZonedDateTime> eventDays;
    private List<ZonedDateTime> activeDays;
    private List<WellnessLectureIdAndDate> wellnessLectureIdAndDateList;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WellnessLectureIdAndDate {
        private Long id;
        private ZonedDateTime startDateTime;
        private Boolean isBooked;
        private Boolean isFullBooking;
    }
}