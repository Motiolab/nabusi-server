package com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UpdateWellnessTicketAdminRequestV1 {
    private Long id;
    private Long centerId;
    private String type;
    private String name;
    private String backgroundColor;
    private String textColor;
    private Double price;
    private String limitType;
    private Integer limitCnt;
    private Integer totalUsableCnt;
    private Integer usableDate;
    private Integer discountValue;
    private Integer salesPrice;
    private List<Long> wellnessClassIdList;
}
