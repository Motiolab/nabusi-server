package com.motiolab.nabusi_server.shop.cartPackage.shopCartItem.application;

import com.motiolab.nabusi_server.shop.cartPackage.shopCartItem.application.dto.ShopCartItemDto;

import java.util.List;

public interface ShopCartItemService {
    ShopCartItemDto getByShopCartIdAndShopProductVariantId(Long shopCartId, Long productVariantId);
    ShopCartItemDto create(ShopCartItemDto shopCartItemDto);
    List<ShopCartItemDto> getAllShopCartId(Long shopCartId);
    ShopCartItemDto update(ShopCartItemDto shopCartItemDto);
    void deleteById(Long id);
}
