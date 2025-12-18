package com.motiolab.nabusi_server.teacher.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetTeacherListByCenterIdAdminResponseV1 {
    private Long id;
    private String name;
    private String nickName;
    private String mobile;
    private Integer lectureCnt;
    private Boolean isDelete;
    private ZonedDateTime createdDate;
}
