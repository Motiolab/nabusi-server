package com.motiolab.nabusi_server.centerPackage.center.application.dto;

import com.motiolab.nabusi_server.centerPackage.center.domain.CenterOpenInfoEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class CenterOpenInfoDto {
    private Long id;
    private Long centerId;
    private ZonedDateTime closeTime;
    private Integer day;
    private Boolean isDayOff;
    private ZonedDateTime openTime;

    public static CenterOpenInfoDto from(CenterOpenInfoEntity centerOpenInfoEntity) {
        return CenterOpenInfoDto.builder()
                .id(centerOpenInfoEntity.getId())
                .centerId(centerOpenInfoEntity.getCenterId())
                .closeTime(centerOpenInfoEntity.getCloseTime())
                .day(centerOpenInfoEntity.getDay())
                .isDayOff(centerOpenInfoEntity.getIsDayOff())
                .openTime(centerOpenInfoEntity.getOpenTime())
                .build();
    }
}
