package com.motiolab.nabusi_server.classPackage.wellnessLectureType.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessLectureType.domain.WellnessLectureTypeEntity;

public record WellnessLectureTypeDto(
        Long id,
        String name,
        String description,
        Long centerId
) {
    public static WellnessLectureTypeDto from(final WellnessLectureTypeEntity wellnessLectureTypeEntity){
        return new WellnessLectureTypeDto(
                wellnessLectureTypeEntity.getId(),
                wellnessLectureTypeEntity.getName(),
                wellnessLectureTypeEntity.getDescription(),
                wellnessLectureTypeEntity.getCenterId()
        );
    }
}


