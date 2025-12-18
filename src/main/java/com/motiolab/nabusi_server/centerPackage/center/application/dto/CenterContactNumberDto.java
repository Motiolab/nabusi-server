package com.motiolab.nabusi_server.centerPackage.center.application.dto;

import com.motiolab.nabusi_server.centerPackage.center.domain.CenterContactNumberEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
public class CenterContactNumberDto {
    @Id
    private Long id;
    private Long centerId;
    private String contactType;
    private String number;

    public static CenterContactNumberDto from(CenterContactNumberEntity centerContactNumberEntity) {
        return CenterContactNumberDto.builder()
                .id(centerContactNumberEntity.getId())
                .centerId(centerContactNumberEntity.getCenterId())
                .contactType(centerContactNumberEntity.getContactType())
                .number(centerContactNumberEntity.getNumber())
                .build();
    }

}
