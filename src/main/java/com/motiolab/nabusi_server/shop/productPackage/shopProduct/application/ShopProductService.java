package com.motiolab.nabusi_server.shop.productPackage.shopProduct.application;

import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductDto;

import java.util.List;

public interface ShopProductService {
    List<ShopProductDto> getAll();

    ShopProductDto getById(Long id);

    List<ShopProductDto> getAllByIdList(List<Long> idList);

    void updateSoldOut(Long id, String soldOut);
}
