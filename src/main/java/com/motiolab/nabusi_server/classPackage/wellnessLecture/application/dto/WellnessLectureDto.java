package com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.domain.WellnessLectureEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder
@Setter
public class WellnessLectureDto {
    private Long id;
    private String name;
    private String description;
    private Long centerId;
    private Integer maxReservationCnt;
    private Long registerId;
    private String room;
    private List<String> lectureImageUrlList;
    private Double price;
    private Long wellnessClassId;
    private Long teacherId;
    private Long wellnessLectureTypeId;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private Boolean isDelete;
    private List<Long> wellnessTicketManagementIdList;

    public static WellnessLectureDto from(WellnessLectureEntity wellnessLectureEntity){
        return WellnessLectureDto.builder()
                .id(wellnessLectureEntity.getId())
                .name(wellnessLectureEntity.getName())
                .description(wellnessLectureEntity.getDescription())
                .centerId(wellnessLectureEntity.getCenterId())
                .maxReservationCnt(wellnessLectureEntity.getMaxReservationCnt())
                .registerId(wellnessLectureEntity.getRegisterId())
                .room(wellnessLectureEntity.getRoom())
                .lectureImageUrlList(wellnessLectureEntity.getLectureImageUrlList())
                .price(wellnessLectureEntity.getPrice())
                .wellnessClassId(wellnessLectureEntity.getWellnessClassId())
                .teacherId(wellnessLectureEntity.getTeacherId())
                .wellnessLectureTypeId(wellnessLectureEntity.getWellnessLectureTypeId())
                .startDateTime(wellnessLectureEntity.getStartDateTime())
                .endDateTime(wellnessLectureEntity.getEndDateTime())
                .isDelete(wellnessLectureEntity.getIsDelete())
                .wellnessTicketManagementIdList(wellnessLectureEntity.getWellnessTicketManagementIdList())
                .build();
    }
}
