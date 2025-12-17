package com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.application.dto;

import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.domain.ShopProductVariantEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class ShopProductVariantDto {
    private Long id;
    private Long shopProductId;
    private String optionName;
    private Integer additionalPrice;
    private String display;
    private String selling;
    private String displaySoldOut;
    private Integer quantity;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static ShopProductVariantDto from(ShopProductVariantEntity shopProductVariantEntity) {
        return ShopProductVariantDto.builder()
                .id(shopProductVariantEntity.getId())
                .shopProductId(shopProductVariantEntity.getShopProductId())
                .optionName(shopProductVariantEntity.getOptionName())
                .additionalPrice(shopProductVariantEntity.getAdditionalPrice())
                .display(shopProductVariantEntity.getDisplay())
                .selling(shopProductVariantEntity.getSelling())
                .displaySoldOut(shopProductVariantEntity.getDisplaySoldOut())
                .quantity(shopProductVariantEntity.getQuantity())
                .lastUpdatedDate(shopProductVariantEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(shopProductVariantEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
