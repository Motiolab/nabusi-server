package com.motiolab.nabusi_server.shop.productPackage.shopProduct.application;

import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductMobileDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.ShopProductReviewService;
import com.motiolab.nabusi_server.shop.productPackage.shopProductReview.application.dto.ShopProductReviewDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.ShopProductVariantService;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopProductMobileServiceImpl implements ShopProductMobileService {
    private final ShopProductService shopProductService;
    private final ShopProductVariantService shopProductVariantService;
    private final ShopProductReviewService shopProductReviewService;

    @Override
    public List<ShopProductMobileDto> getShopProductList() {
        final List<ShopProductDto> shopProductDtoList = shopProductService.getAll();
        final List<Long> shopProductIdList = shopProductDtoList.stream().map(ShopProductDto::getId).toList();

        final List<ShopProductReviewDto> shopProductReviewDtoList = shopProductReviewService.getAllByShopProductIdList(shopProductIdList);
        return shopProductDtoList.stream()
                .map(productDto -> ShopProductMobileDto.builder()
                        .shopProductDto(productDto)
                        .shopProductReviewDtoList(shopProductReviewDtoList.stream()
                                .filter(reviewDto -> reviewDto.getShopProductId().equals(productDto.getId()) && !reviewDto.getIsDelete())
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public ShopProductMobileDto getShopProductDetail(Long shopProductId) {
        final ShopProductDto shopProductDto = shopProductService.getById(shopProductId);
        final List<ShopProductVariantDto> shopProductVariantDtoList = shopProductVariantService.getAllByShopProductId(shopProductId);

        return ShopProductMobileDto.builder()
                .shopProductDto(shopProductDto)
                .shopProductVariantDtoList(shopProductVariantDtoList)
                .build();
    }

}
