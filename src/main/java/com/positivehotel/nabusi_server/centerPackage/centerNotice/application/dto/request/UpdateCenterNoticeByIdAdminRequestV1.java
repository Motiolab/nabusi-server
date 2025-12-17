package com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateCenterNoticeByIdAdminRequestV1 {
    private Long id;
    private Long registerId;
    private String title;
    private String content;
    private Boolean isPopup;
    private Boolean isDelete;
    private Long centerId;
}
