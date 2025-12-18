package com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto;

import com.motiolab.nabusi_server.shop.productPackage.shopProduct.domain.ShopProductEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class ShopProductDto {
    private Long id;
    private String productName;
    private String modelName;
    private Integer price;
    private Integer discountPrice;
    private String display;
    private String mobileDescription;
    private String paymentInfo;
    private String shippingInfo;
    private String exchangeInfo;
    private String serviceInfo;
    private String selling;
    private String simpleDescription;
    private String summaryDescription;
    private String listImage;
    private List<String> detailImage;
    private String hasOption;
    private String soldOut;
    private Integer idx;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static ShopProductDto from(ShopProductEntity shopProductEntity) {
        return ShopProductDto.builder()
                .id(shopProductEntity.getId())
                .productName(shopProductEntity.getProductName())
                .modelName(shopProductEntity.getModelName())
                .price(shopProductEntity.getPrice())
                .discountPrice(shopProductEntity.getDiscountPrice())
                .display(shopProductEntity.getDisplay())
                .mobileDescription(shopProductEntity.getMobileDescription())
                .paymentInfo(shopProductEntity.getPaymentInfo())
                .shippingInfo(shopProductEntity.getShippingInfo())
                .exchangeInfo(shopProductEntity.getExchangeInfo())
                .serviceInfo(shopProductEntity.getServiceInfo())
                .selling(shopProductEntity.getSelling())
                .simpleDescription(shopProductEntity.getSimpleDescription())
                .summaryDescription(shopProductEntity.getSummaryDescription())
                .listImage(shopProductEntity.getListImage())
                .detailImage(shopProductEntity.getDetailImage())
                .hasOption(shopProductEntity.getHasOption())
                .soldOut(shopProductEntity.getSoldOut())
                .idx(shopProductEntity.getIdx())
                .lastUpdatedDate(shopProductEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(shopProductEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
