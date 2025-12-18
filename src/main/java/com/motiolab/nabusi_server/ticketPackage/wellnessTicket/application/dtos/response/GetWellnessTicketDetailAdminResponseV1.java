package com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class GetWellnessTicketDetailAdminResponseV1 {
    Long id;
    String type;
    String name;
    String backgroundColor;
    String textColor;
    Integer discountValue;
    Double price;
    String limitType;
    Integer limitCnt;
    Integer totalUsableCnt;
    Integer usableDate;
    Integer salesPrice;
    Boolean isDelete;
    ZonedDateTime createdDate;
    List<WellnessClassIdAndName> wellnessClassNameList;

    @Builder
    @Getter
    public static class WellnessClassIdAndName {
        Long id;
        String name;
    }
}
