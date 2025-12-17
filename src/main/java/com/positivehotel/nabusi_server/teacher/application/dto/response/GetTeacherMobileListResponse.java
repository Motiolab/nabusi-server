package com.positivehotel.nabusi_server.teacher.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetTeacherMobileListResponse {
    private Long id;
    private String name;
    private String nickName;
    private String imageUrl;
    private Double rating;
    private Integer reviewCnt;
}
