package com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class GetShopProductDetailMobileResponse {
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
    private List<ShopProductVariantResponse> shopProductVariantResponseList;
    private List<ShopProductReviewResponse> shopProductReviewResponseList;

    @Builder
    @Getter
    public static class ShopProductVariantResponse{
        private Long id;
        private String optionName;
        private Integer additionalPrice;
        private String display;
        private String selling;
        private String displaySoldOut;
        private Integer quantity;
    }
    @Builder
    @Getter
    public static class ShopProductReviewResponse{
        private long id;
        private String content;
        private Boolean isPrivate;
        private Integer rating;
        private Long shopProductVariantId;
        private List<String> imageUrlList;
        private Long memberId;
        private Boolean isDelete;
        private Boolean isBest;
        private Integer idx;
        private ZonedDateTime lastUpdatedDate;
        private ZonedDateTime createdDate;
    }
}
