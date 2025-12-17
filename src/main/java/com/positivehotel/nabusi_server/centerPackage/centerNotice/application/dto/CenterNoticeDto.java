package com.positivehotel.nabusi_server.centerPackage.centerNotice.application.dto;

import com.positivehotel.nabusi_server.centerPackage.centerNotice.domain.CenterNoticeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class CenterNoticeDto {
    private Long id;
    private Long centerId;
    private Long registerId;
    private String title;
    private String content;
    private Boolean isPopup;
    private Boolean isDelete;
    private ZonedDateTime createdDate;

    public static CenterNoticeDto from(CenterNoticeEntity centerNoticeEntity) {
        return CenterNoticeDto.builder()
                .id(centerNoticeEntity.getId())
                .centerId(centerNoticeEntity.getCenterId())
                .registerId(centerNoticeEntity.getRegisterId())
                .title(centerNoticeEntity.getTitle())
                .content(centerNoticeEntity.getContent())
                .isPopup(centerNoticeEntity.getIsPopup())
                .isDelete(centerNoticeEntity.getIsDelete())
                .createdDate(centerNoticeEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
