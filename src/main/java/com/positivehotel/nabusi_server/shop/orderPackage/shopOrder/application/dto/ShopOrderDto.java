package com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application.dto;

import com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.domain.ShopOrderEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ShopOrderDto {
    private Long id;
    private Long memberId;
    private Long paymentId;
    private Boolean purchaseConfirmation;
    private String status;
    private Integer totalPrice;
    private Integer totalDiscountPrice;
    private Integer totalAdditionalPrice;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String receiverAddressCode;
    private String receiverDetailAddress;
    private Long usedPoint;
    private Long rewardPoint;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime createdDate;

    public static ShopOrderDto from(ShopOrderEntity shopOrderEntity) {
        return ShopOrderDto.builder()
                .id(shopOrderEntity.getId())
                .memberId(shopOrderEntity.getMemberId())
                .paymentId(shopOrderEntity.getPaymentId())
                .purchaseConfirmation(shopOrderEntity.getPurchaseConfirmation())
                .status(shopOrderEntity.getStatus())
                .totalPrice(shopOrderEntity.getTotalPrice())
                .totalDiscountPrice(shopOrderEntity.getTotalDiscountPrice())
                .totalAdditionalPrice(shopOrderEntity.getTotalAdditionalPrice())
                .receiverName(shopOrderEntity.getReceiverName())
                .receiverPhone(shopOrderEntity.getReceiverPhone())
                .receiverAddress(shopOrderEntity.getReceiverAddress())
                .receiverAddressCode(shopOrderEntity.getReceiverAddressCode())
                .usedPoint(shopOrderEntity.getUsedPoint())
                .rewardPoint(shopOrderEntity.getRewardPoint())
                .lastUpdatedDate(shopOrderEntity.getLastUpdatedDate())
                .createdDate(shopOrderEntity.getCreatedDate())
                .build();
    }
}
