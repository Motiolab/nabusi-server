package com.motiolab.nabusi_server.shop.cartPackage.shopCart.application;

import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartDto;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;

public interface ShopCartService {
    ShopCartDto getByMemberIdAndStatus(Long memberId, CartStatus cartStatus);

    ShopCartDto create(ShopCartDto shopCartDto);

    void updateStatus(Long id, CartStatus status);

}
