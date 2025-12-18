package com.motiolab.nabusi_server.classPackage.wellnessClass.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetWellnessClassNameByCenterIdAdminResponseV1 {
    private Long id;
    private String name;
    private String teacherName;
}
