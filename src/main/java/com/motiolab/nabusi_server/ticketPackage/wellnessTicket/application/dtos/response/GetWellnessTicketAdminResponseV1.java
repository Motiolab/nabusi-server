package com.motiolab.nabusi_server.ticketPackage.wellnessTicket.application.dtos.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class GetWellnessTicketAdminResponseV1 {
    Long id;
    String type;
    String name;
    String backgroundColor;
    String textColor;
    String limitType;
    Integer limitCnt;
    Integer totalUsableCnt;
    Integer usableDate;
    Integer salesPrice;
    Double price;
    Integer discountValue;
    Boolean isDelete;
    ZonedDateTime createdDate;
}
