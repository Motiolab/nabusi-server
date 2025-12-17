package com.positivehotel.nabusi_server.teacher.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateTeacherCareerByIdAdminRequestV1 {
    private Long id;
    private String career;
}
