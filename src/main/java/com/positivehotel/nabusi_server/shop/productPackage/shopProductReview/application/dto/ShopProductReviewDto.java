package com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application.dto;

import com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.domain.ShopProductReviewEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class ShopProductReviewDto {
    private long id;
    private String content;
    private Boolean isPrivate;
    private Integer rating;
    private Long shopOrderId;
    private Long shopProductId;
    private Long shopProductVariantId;
    private List<String> imageUrlList;
    private Long memberId;
    private Boolean isDelete;
    private Boolean isBest;
    private Integer idx;
    private ZonedDateTime lastUpdatedDate;
    private ZonedDateTime createdDate;

    public static ShopProductReviewDto from(ShopProductReviewEntity shopProductReviewEntity) {
        return ShopProductReviewDto.builder()
                .id(shopProductReviewEntity.getId())
                .content(shopProductReviewEntity.getContent())
                .isPrivate(shopProductReviewEntity.getIsPrivate())
                .rating(shopProductReviewEntity.getRating())
                .shopOrderId(shopProductReviewEntity.getShopOrderId())
                .shopProductId(shopProductReviewEntity.getShopProductId())
                .shopProductVariantId(shopProductReviewEntity.getShopProductVariantId())
                .imageUrlList(shopProductReviewEntity.getImageUrlList())
                .memberId(shopProductReviewEntity.getMemberId())
                .isDelete(shopProductReviewEntity.getIsDelete())
                .isBest(shopProductReviewEntity.getIsBest())
                .idx(shopProductReviewEntity.getIdx())
                .lastUpdatedDate(shopProductReviewEntity.getLastUpdatedDate().atZone(ZoneId.of("UTC")))
                .createdDate(shopProductReviewEntity.getCreatedDate().atZone(ZoneId.of("UTC")))
                .build();
    }
}
