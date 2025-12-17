package com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.domain.ShopCartEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ShopCartDto {
    private Long id;
    private Long memberId;
    private CartStatus status;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime createdDate;

    public static ShopCartDto from(ShopCartEntity shopCartEntity) {
        return ShopCartDto.builder()
                .id(shopCartEntity.getId())
                .memberId(shopCartEntity.getMemberId())
                .status(shopCartEntity.getStatus())
                .lastUpdatedDate(shopCartEntity.getLastUpdatedDate())
                .createdDate(shopCartEntity.getCreatedDate())
                .build();
    }
}
