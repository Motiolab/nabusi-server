package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetAllWellnessLectureListByCenterIdAndDateResponse {
    private Long id;
    private String lectureImageUrl;
    private String status;
    private String name;
    private String teacherName;
    private String room;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Boolean isFullBooking;
    private String wellnessLectureTypeName;
    private String wellnessLectureTypeDescription;
    private Boolean isCreatedReview;
}
