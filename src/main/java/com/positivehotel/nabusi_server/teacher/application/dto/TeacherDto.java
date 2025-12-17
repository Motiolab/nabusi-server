package com.positivehotel.nabusi_server.teacher.application.dto;

import com.positivehotel.nabusi_server.teacher.domain.TeacherEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class TeacherDto {
    private Long id;
    private String name;
    private String nickName;
    private String color;
    private String email;
    private String mobile;
    private String introduce;
    private String career;
    private Long centerId;
    private Long memberId;
    private Boolean useNickName;
    private String imageUrl;
    private Boolean isDelete;
    private ZonedDateTime createdDate;

    public static TeacherDto from(TeacherEntity teacherEntity) {
        return TeacherDto.builder()
                .id(teacherEntity.getId())
                .name(teacherEntity.getName())
                .nickName(teacherEntity.getNickName())
                .color(teacherEntity.getColor())
                .email(teacherEntity.getEmail())
                .mobile(teacherEntity.getMobile())
                .introduce(teacherEntity.getIntroduce())
                .career(teacherEntity.getCareer())
                .centerId(teacherEntity.getCenterId())
                .memberId(teacherEntity.getMemberId())
                .useNickName(teacherEntity.getUseNickName())
                .imageUrl(teacherEntity.getImageUrl())
                .isDelete(teacherEntity.getIsDelete())
                .createdDate(teacherEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
