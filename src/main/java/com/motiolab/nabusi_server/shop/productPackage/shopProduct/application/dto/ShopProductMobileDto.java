package com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto;

import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ShopProductMobileDto {
    private ShopProductDto shopProductDto;
    private List<ShopProductVariantDto> shopProductVariantDtoList;
    private List<ShopProductReviewDto> shopProductReviewDtoList;
}
