package com.motiolab.nabusi_server.centerPackage.center.application.dto;

import com.motiolab.nabusi_server.centerPackage.center.domain.CenterEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CenterDto {
    private Long id;
    private String name;
    private String address;
    private String code;
    private String detailAddress;
    private String roadName;
    private String description;
    private Boolean isActive;
    private List<Long> memberIdList;
    private List<String> imageUrlList;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime createdDate;

    public static CenterDto from(CenterEntity centerEntity) {
        return CenterDto.builder()
                .id(centerEntity.getId())
                .name(centerEntity.getName())
                .address(centerEntity.getAddress())
                .code(centerEntity.getCode())
                .detailAddress(centerEntity.getDetailAddress())
                .roadName(centerEntity.getRoadName())
                .description(centerEntity.getDescription())
                .isActive(centerEntity.getIsActive())
                .memberIdList(centerEntity.getMemberIdList())
                .imageUrlList(centerEntity.getImageUrlList())
                .lastUpdatedDate(centerEntity.getLastUpdatedDate())
                .createdDate(centerEntity.getCreatedDate())
                .build();
    }
}
