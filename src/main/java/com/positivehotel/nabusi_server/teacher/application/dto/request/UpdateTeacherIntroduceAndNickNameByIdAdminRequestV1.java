package com.positivehotel.nabusi_server.teacher.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateTeacherIntroduceAndNickNameByIdAdminRequestV1 {
    private Long id;
    private Boolean useNickName;
    private String nickName;
    private String introduce;
}
