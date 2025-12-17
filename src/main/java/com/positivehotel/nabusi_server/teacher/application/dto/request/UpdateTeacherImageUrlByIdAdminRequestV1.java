package com.positivehotel.nabusi_server.teacher.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateTeacherImageUrlByIdAdminRequestV1 {
    private Long id;
    private String imageUrl;
}
