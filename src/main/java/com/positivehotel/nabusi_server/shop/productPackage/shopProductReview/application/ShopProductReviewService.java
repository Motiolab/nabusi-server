package com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application;

import com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewDto;

import java.util.List;

public interface ShopProductReviewService {
    void create(ShopProductReviewDto shopProductReviewDto);
    List<ShopProductReviewDto> getAllByShopProductId(Long shopProductId);
    List<ShopProductReviewDto> getAllByShopProductIdList(List<Long> shopProductIdList);
}
