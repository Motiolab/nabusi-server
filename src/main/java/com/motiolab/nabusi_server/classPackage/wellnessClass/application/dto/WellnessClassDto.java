package com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto;

import com.motiolab.nabusi_server.classPackage.wellnessClass.domain.WellnessClassEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class WellnessClassDto {
    private Long id;
    private String name;
    private String description;
    private Long centerId;
    private Integer maxReservationCnt;
    private Long registerId;
    private String room;
    private List<String> classImageUrlList;
    private Long teacherId;
    private Long wellnessLectureTypeId;
    private Boolean isDelete;
    private List<Long> wellnessTicketManagementIdList;

    public static WellnessClassDto from(WellnessClassEntity wellnessClassEntity) {
        return WellnessClassDto.builder()
                .id(wellnessClassEntity.getId())
                .name(wellnessClassEntity.getName())
                .description(wellnessClassEntity.getDescription())
                .centerId(wellnessClassEntity.getCenterId())
                .maxReservationCnt(wellnessClassEntity.getMaxReservationCnt())
                .registerId(wellnessClassEntity.getRegisterId())
                .room(wellnessClassEntity.getRoom())
                .classImageUrlList(wellnessClassEntity.getClassImageUrlList())
                .teacherId(wellnessClassEntity.getTeacherId())
                .wellnessLectureTypeId(wellnessClassEntity.getWellnessLectureTypeId())
                .isDelete(wellnessClassEntity.getIsDelete())
                .wellnessTicketManagementIdList(wellnessClassEntity.getWellnessTicketManagementIdList())
                .build();
    }
}
