package com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto;

import com.motiolab.nabusi_server.ticketPackage.enums.PaymentMethodEnum;
import com.motiolab.nabusi_server.ticketPackage.wellnessTicketIssuance.domain.WellnessTicketIssuanceEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Builder
@Setter
public class WellnessTicketIssuanceDto {
    private Long id;
    private Long centerId;
    private Long paymentId;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime expireDate;
    private String type;
    private String backgroundColor;
    private String textColor;
    private String limitType;
    private Integer limitCnt;
    private Boolean isDelete;
    private Integer remainingCnt;
    private Integer totalUsableCnt;
    private Long memberId;
    private Long wellnessTicketId;
    private Boolean isSendExpireNotification;
    private Boolean isSendRemainingCntNotification;
    private PaymentMethodEnum paymentMethod;
    private Integer unpaidValue;
    private ZonedDateTime createdDate;

    public static WellnessTicketIssuanceDto from(WellnessTicketIssuanceEntity wellnessTicketIssuanceEntity) {
        return WellnessTicketIssuanceDto.builder()
                .id(wellnessTicketIssuanceEntity.getId())
                .centerId(wellnessTicketIssuanceEntity.getCenterId())
                .paymentId(wellnessTicketIssuanceEntity.getPaymentId())
                .name(wellnessTicketIssuanceEntity.getName())
                .startDate(wellnessTicketIssuanceEntity.getStartDate())
                .expireDate(wellnessTicketIssuanceEntity.getExpireDate())
                .type(wellnessTicketIssuanceEntity.getType())
                .backgroundColor(wellnessTicketIssuanceEntity.getBackgroundColor())
                .textColor(wellnessTicketIssuanceEntity.getTextColor())
                .limitType(wellnessTicketIssuanceEntity.getLimitType())
                .limitCnt(wellnessTicketIssuanceEntity.getLimitCnt())
                .isDelete(wellnessTicketIssuanceEntity.getIsDelete())
                .remainingCnt(wellnessTicketIssuanceEntity.getRemainingCnt())
                .totalUsableCnt(wellnessTicketIssuanceEntity.getTotalUsableCnt())
                .memberId(wellnessTicketIssuanceEntity.getMemberId())
                .wellnessTicketId(wellnessTicketIssuanceEntity.getWellnessTicketId())
                .isSendExpireNotification(wellnessTicketIssuanceEntity.getIsSendExpireNotification())
                .isSendRemainingCntNotification(wellnessTicketIssuanceEntity.getIsSendRemainingCntNotification())
                .paymentMethod(wellnessTicketIssuanceEntity.getPaymentMethod())
                .unpaidValue(wellnessTicketIssuanceEntity.getUnpaidValue())
                .createdDate(wellnessTicketIssuanceEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
