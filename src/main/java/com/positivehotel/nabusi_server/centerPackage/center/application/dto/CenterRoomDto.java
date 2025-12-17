package com.positivehotel.nabusi_server.centerPackage.center.application.dto;

import com.positivehotel.nabusi_server.centerPackage.center.domain.CenterRoomEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CenterRoomDto {
    private Long id;
    private Long centerId;
    private String name;
    private Integer capacity;

    public static CenterRoomDto from(CenterRoomEntity centerRoomEntity) {
        return CenterRoomDto.builder()
                .id(centerRoomEntity.getId())
                .centerId(centerRoomEntity.getCenterId())
                .name(centerRoomEntity.getName())
                .capacity(centerRoomEntity.getCapacity())
                .build();
    }
}
