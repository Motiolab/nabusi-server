package com.positivehotel.nabusi_server.teacher.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetTeacherDetailById {
    private Long id;
    private String name;
    private String nickName;
    private String mobile;
    private String email;
    private String introduce;
    private String career;
    private Boolean useNickName;
    private String imageUrl;
    private Boolean isDelete;
    private ZonedDateTime createdDate;
}
