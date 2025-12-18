package com.motiolab.nabusi_server.shop.productPackage.shopProductReview.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.ShopProductReviewMobileService;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewMobileDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.dto.request.CreateShopProductReviewRequest;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.dto.response.CreateShopProductReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShopProductReviewMobileController {
    private final ShopProductReviewMobileService shopProductReviewMobileService;

    @PostMapping("/v1/mobile/shop/product-review/create")
    public ResponseEntity<CreateShopProductReviewResponse> createShopProductReview(final @MemberId Long memberId, final @RequestBody CreateShopProductReviewRequest createShopProductReviewRequest){
        createShopProductReviewRequest.setMemberId(memberId);
        shopProductReviewMobileService.createShopProductReview(createShopProductReviewRequest);
        return ResponseEntity.ok(CreateShopProductReviewResponse.builder().success(true).build());
    }

    @GetMapping("/v1/mobile/shop/product-review/{productId}")
    public ResponseEntity<List<ShopProductReviewMobileDto>> getShopProductReviewListByShopProductId(final @PathVariable Long shopProductId) {
        List<ShopProductReviewMobileDto> shopProductReviewMobileDtoList = shopProductReviewMobileService.getAllByShopProductId(shopProductId);
        return ResponseEntity.ok(shopProductReviewMobileDtoList);
    }
}
