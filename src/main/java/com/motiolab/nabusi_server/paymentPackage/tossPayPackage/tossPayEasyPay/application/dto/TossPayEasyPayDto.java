package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.application.dto;

import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.domain.TossPayEasyPayEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TossPayEasyPayDto {
    private Long id;
    private String provider;
    private Integer amount;
    private Integer discountAmount;

    public static TossPayEasyPayDto from(TossPayEasyPayEntity tossPayEasyPayEntity) {
        return TossPayEasyPayDto.builder()
                .id(tossPayEasyPayEntity.getId())
                .provider(tossPayEasyPayEntity.getProvider())
                .amount(tossPayEasyPayEntity.getAmount())
                .discountAmount(tossPayEasyPayEntity.getDiscountAmount())
                .build();
    }
}
