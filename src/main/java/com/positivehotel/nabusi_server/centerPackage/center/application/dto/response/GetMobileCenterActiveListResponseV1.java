package com.positivehotel.nabusi_server.centerPackage.center.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetMobileCenterActiveListResponseV1 {
    private Long id;
    private String name;
    private String address;
    private String detailAddress;
    private String description;
    private List<String> imageUrlList;
    private Double rating;
    private Integer reviewCnt;
}
