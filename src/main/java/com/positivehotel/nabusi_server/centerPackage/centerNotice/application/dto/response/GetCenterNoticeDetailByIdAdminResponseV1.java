package com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class GetCenterNoticeDetailByIdAdminResponseV1 {
    private Long id;
    private String title;
    private String content;
    private Boolean isPopup;
    private Boolean isDelete;
    private ZonedDateTime createdDate;
}
