package com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.application.dtos;

import com.positivehotel.nabusi_server.ticketPackage.wellnessTicket.domain.WellnessTicketEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class WellnessTicketDto{
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
    private List<Long> wellnessClassIdList;
    private Integer salesPrice;
    private Boolean isDelete;
    private LocalDateTime createdDate;

    public static WellnessTicketDto from(WellnessTicketEntity wellnessTicketEntity) {
        return WellnessTicketDto.builder()
                .id(wellnessTicketEntity.getId())
                .centerId(wellnessTicketEntity.getCenterId())
                .type(wellnessTicketEntity.getType())
                .name(wellnessTicketEntity.getName())
                .backgroundColor(wellnessTicketEntity.getBackgroundColor())
                .textColor(wellnessTicketEntity.getTextColor())
                .price(wellnessTicketEntity.getPrice())
                .limitType(wellnessTicketEntity.getLimitType())
                .limitCnt(wellnessTicketEntity.getLimitCnt())
                .totalUsableCnt(wellnessTicketEntity.getTotalUsableCnt())
                .usableDate(wellnessTicketEntity.getUsableDate())
                .discountValue(wellnessTicketEntity.getDiscountValue())
                .wellnessClassIdList(wellnessTicketEntity.getWellnessClassIdList())
                .salesPrice(wellnessTicketEntity.getSalesPrice())
                .isDelete(wellnessTicketEntity.getIsDelete())
                .createdDate(wellnessTicketEntity.getCreatedDate())
                .build();
    }
}
