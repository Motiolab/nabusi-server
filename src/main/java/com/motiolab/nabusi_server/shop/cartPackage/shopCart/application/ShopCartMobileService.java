package com.motiolab.nabusi_server.shop.cartPackage.shopCart.application;

import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartMobileDto;

import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;

public interface ShopCartMobileService {
    ShopCartMobileDto getCartByMemberIdAndStatus(Long memberId, CartStatus status);
}
