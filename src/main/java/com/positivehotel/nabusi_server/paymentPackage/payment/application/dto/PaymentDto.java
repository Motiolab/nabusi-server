package com.positivehotel.nabusi_server.paymentPackage.payment.application.dto;

import com.positivehotel.nabusi_server.paymentPackage.payment.domain.PaymentEntity;
import com.positivehotel.nabusi_server.paymentPackage.payment.enums.PaymentStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class PaymentDto {
    private Long id;
    private Long memberId;
    private Long tossPayId;
    private Long onSitePayId;
    private PaymentStatusEnum status;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static PaymentDto from(final PaymentEntity paymentEntity) {
        return PaymentDto.builder()
                .id(paymentEntity.getId())
                .memberId(paymentEntity.getMemberId())
                .tossPayId(paymentEntity.getTossPayId())
                .onSitePayId(paymentEntity.getOnSitePayId())
                .status(paymentEntity.getStatus())
                .createdDate(paymentEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .lastUpdatedDate(paymentEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
