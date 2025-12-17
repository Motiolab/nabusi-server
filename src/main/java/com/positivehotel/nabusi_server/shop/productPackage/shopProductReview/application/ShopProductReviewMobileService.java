package com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application;

import com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewMobileDto;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductReview.application.dto.request.CreateShopProductReviewRequest;

import java.util.List;

public interface ShopProductReviewMobileService {
    void createShopProductReview(CreateShopProductReviewRequest createShopProductReviewRequest);
    List<ShopProductReviewMobileDto> getAllByShopProductId(Long shopProductId);
}
