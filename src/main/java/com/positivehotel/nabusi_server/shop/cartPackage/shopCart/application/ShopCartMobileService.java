package com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartMobileDto;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;

public interface ShopCartMobileService {
    ShopCartMobileDto getCartByMemberIdAndStatus(Long memberId, CartStatus status);
}
