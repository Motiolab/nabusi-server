package com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetWellnessClassDetailWithLectureAdminResponseV1 {
    Long id;
    String wellnessClassName;
    String wellnessClassDescription;
    String teacherName;
    String teacherImageUrl;
    String room;
    String lectureType;
    String pastClassesCount;
    Integer upcomingClassesCount;
    List<Ticket> ticketList;
    List<String> wellnessClassImageUrlList;
    List<Lecture> lectureList;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Ticket {
        String name;
        String type;
        String backgroundColor;
        String textColor;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Lecture {
        Long id;
        String name;
        ZonedDateTime startDateTime;
        ZonedDateTime endDateTime;
        Integer maxReservationCnt;
        Integer currentReservationCnt;
        Boolean isDelete;
        Boolean isPast;
    }
}
