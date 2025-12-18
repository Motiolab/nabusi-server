package com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CreateShopProductReviewRequest {
    private String content;
    private Boolean isPrivate;
    private Integer rating;
    private Long shopOrderId;
    private Long shopProductId;
    private Long shopProductVariantId;
    private List<String> imageUrlList;
    private Long memberId;
}
