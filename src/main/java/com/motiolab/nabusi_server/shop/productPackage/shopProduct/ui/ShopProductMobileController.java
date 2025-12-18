package com.motiolab.nabusi_server.shop.productPackage.shopProduct.ui;

import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.ShopProductMobileService;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductMobileDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.response.GetShopProductDetailMobileResponse;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.response.GetShopProductListMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ShopProductMobileController {
    private final ShopProductMobileService shopProductMobileService;

    @GetMapping("/v1/mobile/shop/products")
    public ResponseEntity<List<GetShopProductListMobileResponse>> getShopProducts() {
        final List<ShopProductMobileDto> shopProductMobileDtoList = shopProductMobileService.getShopProductList();
        List<GetShopProductListMobileResponse> getShopProductListMobileResponseList = shopProductMobileDtoList
                .stream()
                .map(shopProductMobileDto ->
                        GetShopProductListMobileResponse.builder()
                                .id(shopProductMobileDto.getShopProductDto().getId())
                                .productName(shopProductMobileDto.getShopProductDto().getProductName())
                                .modelName(shopProductMobileDto.getShopProductDto().getModelName())
                                .price(shopProductMobileDto.getShopProductDto().getPrice())
                                .discountPrice(shopProductMobileDto.getShopProductDto().getDiscountPrice())
                                .display(shopProductMobileDto.getShopProductDto().getDisplay())
                                .selling(shopProductMobileDto.getShopProductDto().getSelling())
                                .listImage(shopProductMobileDto.getShopProductDto().getListImage())
                                .soldOut(shopProductMobileDto.getShopProductDto().getSoldOut())
                                .idx(shopProductMobileDto.getShopProductDto().getIdx())
                                .shopProductReviewCount(shopProductMobileDto.getShopProductReviewDtoList().size())
                                .build())
                .toList();

        return ResponseEntity.ok(getShopProductListMobileResponseList);
    }

    @GetMapping("/v1/mobile/shop/product/detail/{productId}")
    public ResponseEntity<GetShopProductDetailMobileResponse> getShopProductDetail(@PathVariable Long productId) {
        final ShopProductMobileDto shopProductMobileDto = shopProductMobileService.getShopProductDetail(productId);

        final GetShopProductDetailMobileResponse shopProductDetailMobileResponse = GetShopProductDetailMobileResponse.builder()
                .id(shopProductMobileDto.getShopProductDto().getId())
                .productName(shopProductMobileDto.getShopProductDto().getProductName())
                .modelName(shopProductMobileDto.getShopProductDto().getModelName())
                .price(shopProductMobileDto.getShopProductDto().getPrice())
                .discountPrice(shopProductMobileDto.getShopProductDto().getDiscountPrice())
                .display(shopProductMobileDto.getShopProductDto().getDisplay())
                .mobileDescription(shopProductMobileDto.getShopProductDto().getMobileDescription())
                .paymentInfo(shopProductMobileDto.getShopProductDto().getPaymentInfo())
                .shippingInfo(shopProductMobileDto.getShopProductDto().getShippingInfo())
                .exchangeInfo(shopProductMobileDto.getShopProductDto().getExchangeInfo())
                .serviceInfo(shopProductMobileDto.getShopProductDto().getServiceInfo())
                .selling(shopProductMobileDto.getShopProductDto().getSelling())
                .simpleDescription(shopProductMobileDto.getShopProductDto().getSimpleDescription())
                .summaryDescription(shopProductMobileDto.getShopProductDto().getSummaryDescription())
                .listImage(shopProductMobileDto.getShopProductDto().getListImage())
                .detailImage(shopProductMobileDto.getShopProductDto().getDetailImage())
                .hasOption(shopProductMobileDto.getShopProductDto().getHasOption())
                .soldOut(shopProductMobileDto.getShopProductDto().getSoldOut())
                .idx(shopProductMobileDto.getShopProductDto().getIdx())
                .lastUpdatedDate(shopProductMobileDto.getShopProductDto().getLastUpdatedDate())
                .createdDate(shopProductMobileDto.getShopProductDto().getCreatedDate())
                .shopProductVariantResponseList(Optional.ofNullable(shopProductMobileDto.getShopProductVariantDtoList())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(shopProductVariantDto -> GetShopProductDetailMobileResponse.ShopProductVariantResponse.builder()
                                .id(shopProductVariantDto.getId())
                                .optionName(shopProductVariantDto.getOptionName())
                                .additionalPrice(shopProductVariantDto.getAdditionalPrice())
                                .display(shopProductVariantDto.getDisplay())
                                .selling(shopProductVariantDto.getSelling())
                                .displaySoldOut(shopProductVariantDto.getDisplaySoldOut())
                                .quantity(shopProductVariantDto.getQuantity())
                                .build())
                        .toList())
                .shopProductReviewResponseList(Optional.ofNullable(shopProductMobileDto.getShopProductReviewDtoList())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(shopProductReviewDto -> GetShopProductDetailMobileResponse.ShopProductReviewResponse.builder()
                                .id(shopProductReviewDto.getId())
                                .content(shopProductReviewDto.getContent())
                                .isPrivate(shopProductReviewDto.getIsPrivate())
                                .rating(shopProductReviewDto.getRating())
                                .shopProductVariantId(shopProductReviewDto.getShopProductVariantId())
                                .imageUrlList(shopProductReviewDto.getImageUrlList())
                                .memberId(shopProductReviewDto.getMemberId())
                                .isDelete(shopProductReviewDto.getIsDelete())
                                .isBest(shopProductReviewDto.getIsBest())
                                .idx(shopProductReviewDto.getIdx())
                                .lastUpdatedDate(shopProductReviewDto.getLastUpdatedDate())
                                .createdDate(shopProductReviewDto.getCreatedDate())
                                .build())
                        .toList())
                .build();

        return ResponseEntity.ok(shopProductDetailMobileResponse);
    }
}
