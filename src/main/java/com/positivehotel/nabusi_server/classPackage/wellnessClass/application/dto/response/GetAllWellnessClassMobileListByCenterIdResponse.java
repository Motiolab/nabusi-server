package com.positivehotel.nabusi_server.classPackage.wellnessClass.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAllWellnessClassMobileListByCenterIdResponse {
    private Long id;
    private String name;
    private String description;
    private String classImageUrl;
    private Integer maxReservationCnt;
    private String teacherName;
    private String room;
    private String wellnessLectureTypeName;
    private String wellnessLectureTypeDescription;
    private Double rating;
    private Integer reviewCnt;
}
