package com.positivehotel.nabusi_server.paymentPackage.onSitePay.application.dto;

import com.positivehotel.nabusi_server.paymentPackage.onSitePay.domain.OnSitePayEntity;
import com.positivehotel.nabusi_server.ticketPackage.enums.PaymentMethodEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@Getter
public class OnSitePayDto {
    private Long id;
    private Long paymentId;
    private Integer discountRate;
    private Integer totalPayValue;
    private Integer unpaidValue;
    private Integer cardInstallment;
    private Integer cardPayValue;
    private Integer cashPayValue;
    private Long payerMemberId;
    private Long payeeMemberId;
    private String note;
    private ZonedDateTime paymentDate;
    private PaymentMethodEnum paymentMethod;
    private Long updateMemberId;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime createdDate;

    public static OnSitePayDto from(OnSitePayEntity onSitePayEntity) {
        return OnSitePayDto.builder()
                .id(onSitePayEntity.getId())
                .paymentId(onSitePayEntity.getPaymentId())
                .discountRate(onSitePayEntity.getDiscountRate())
                .totalPayValue(onSitePayEntity.getTotalPayValue())
                .unpaidValue(onSitePayEntity.getUnpaidValue())
                .cardInstallment(onSitePayEntity.getCardInstallment())
                .cardPayValue(onSitePayEntity.getCardPayValue())
                .cashPayValue(onSitePayEntity.getCashPayValue())
                .payerMemberId(onSitePayEntity.getPayerMemberId())
                .payeeMemberId(onSitePayEntity.getPayeeMemberId())
                .note(onSitePayEntity.getNote())
                .paymentDate(onSitePayEntity.getPaymentDate())
                .paymentMethod(onSitePayEntity.getPaymentMethod())
                .updateMemberId(onSitePayEntity.getUpdateMemberId())
                .lastUpdatedDate(onSitePayEntity.getLastUpdatedDate())
                .createdDate(onSitePayEntity.getCreatedDate())
                .build();
    }
}
