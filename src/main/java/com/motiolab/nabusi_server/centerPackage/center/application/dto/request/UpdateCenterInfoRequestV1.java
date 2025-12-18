package com.motiolab.nabusi_server.centerPackage.center.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCenterInfoRequestV1 {
    private Long id;
    private String centerName;
    private String address;
    private String detailAddress;
    private String roadName;
    private String code;
    private List<String> imageUrlList;
    private String description;
    private List<CenterOpenInfo> openInfoList;
    private List<CenterRoom> roomList;
    private List<CenterContactNumber> contactNumberList;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CenterOpenInfo {
        ZonedDateTime closeTime;
        Integer day;
        Boolean isDayOff;
        ZonedDateTime openTime;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CenterRoom {
        String name;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CenterContactNumber{
        private String contactType;
        private String number;
    }
}
