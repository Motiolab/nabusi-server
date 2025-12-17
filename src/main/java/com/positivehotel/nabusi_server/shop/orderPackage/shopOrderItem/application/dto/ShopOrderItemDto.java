package com.positivehotel.nabusi_server.shop.orderPackage.shopOrderItem.application.dto;

import com.positivehotel.nabusi_server.shop.orderPackage.shopOrderItem.domain.ShopOrderItemEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShopOrderItemDto {
    private Long id;
    private Long shopOrderId;
    private Long shopProductVariantId;
    private Long shopProductId;
    private String optionName;
    private Integer additionalPrice;
    private String productName;
    private Integer price;
    private Integer productPrice;
    private String listImage;
    private String detailImage;
    private Integer quantity;
    private Integer totalPrice;

    public static ShopOrderItemDto from(ShopOrderItemEntity shopOrderItemEntity) {
        return ShopOrderItemDto.builder()
                .id(shopOrderItemEntity.getId())
                .shopOrderId(shopOrderItemEntity.getShopOrderId())
                .shopProductVariantId(shopOrderItemEntity.getShopProductVariantId())
                .shopProductId(shopOrderItemEntity.getShopProductId())
                .optionName(shopOrderItemEntity.getOptionName())
                .additionalPrice(shopOrderItemEntity.getAdditionalPrice())
                .productName(shopOrderItemEntity.getProductName())
                .price(shopOrderItemEntity.getPrice())
                .productPrice(shopOrderItemEntity.getProductPrice())
                .listImage(shopOrderItemEntity.getListImage())
                .detailImage(shopOrderItemEntity.getDetailImage())
                .quantity(shopOrderItemEntity.getQuantity())
                .totalPrice(shopOrderItemEntity.getTotalPrice())
                .build();
    }
}
