package com.positivehotel.nabusi_server.centerPackage.center.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetCenterInfoResponseV1 {
    Long id;
    String address;
    String code;
    String detailAddress;
    String roadName;
    String description;
    String name;
    List<String> imageUrlList;
    List<CenterOpenInfo> openInfoList;
    List<CenterRoom> roomList;
    List<CenterContactNumber> contactNumberList;

    @Builder
    @Getter
    public static class CenterOpenInfo {
        Long id;
        ZonedDateTime closeTime;
        Integer day;
        Boolean isDayOff;
        ZonedDateTime openTime;
    }
    @Builder
    @Getter
    public static class CenterRoom {
        Long id;
        String name;
        Integer capacity;
    }
    @Builder
    @Getter
    public static class CenterContactNumber{
        Long id;
        String contactType;
        String number;
    }


}
