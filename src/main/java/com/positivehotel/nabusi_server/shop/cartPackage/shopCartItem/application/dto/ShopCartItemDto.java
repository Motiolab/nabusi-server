package com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.domain.ShopCartItemEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ShopCartItemDto {
    private Long id;
    private Long shopCartId;
    private Long shopProductId;
    private Long shopProductVariantId;
    private Integer quantity;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime createdDate;

    public static ShopCartItemDto from(ShopCartItemEntity shopCartItemEntity){
        return ShopCartItemDto.builder()
                .id(shopCartItemEntity.getId())
                .shopCartId(shopCartItemEntity.getShopCartId())
                .shopProductId(shopCartItemEntity.getShopProductId())
                .shopProductVariantId(shopCartItemEntity.getShopProductVariantId())
                .quantity(shopCartItemEntity.getQuantity())
                .lastUpdatedDate(shopCartItemEntity.getLastUpdatedDate())
                .createdDate(shopCartItemEntity.getCreatedDate())
                .build();
    }
}
