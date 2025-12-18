package com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application;

import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;

import java.util.List;

public interface ShopProductVariantService {
    List<ShopProductVariantDto> getAllByShopProductId(Long productId);
    List<ShopProductVariantDto> getAllByIdList(List<Long> idList);
    ShopProductVariantDto getById(Long id);
    ShopProductVariantDto update(ShopProductVariantDto shopProductVariantDto);
}
