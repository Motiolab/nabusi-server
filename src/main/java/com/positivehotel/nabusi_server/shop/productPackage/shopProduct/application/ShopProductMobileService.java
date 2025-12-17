package com.positivehotel.nabusi_server.shop.productPackage.shopProduct.application;

import com.positivehotel.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductMobileDto;

import java.util.List;

public interface ShopProductMobileService {
    List<ShopProductMobileDto> getShopProductList();
    ShopProductMobileDto getShopProductDetail(Long shopProductId);
}
