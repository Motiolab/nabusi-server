package com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetShopProductListMobileResponse {
    private Long id;
    private String productName;
    private String modelName;
    private Integer price;
    private Integer discountPrice;
    private String display;
    private String selling;
    private String listImage;
    private String soldOut;
    private Integer idx;
    private Integer shopProductReviewCount;
}
