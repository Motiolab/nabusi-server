package com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartMobileDto;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.request.CreateCartItemMobileRequestV1;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.request.UpdateCartItemMobileRequestV1;

public interface ShopCartItemMobileService {
    ShopCartMobileDto createCartItem(CreateCartItemMobileRequestV1 createCartItemMobileRequestV1);
    Boolean updateCartItem(UpdateCartItemMobileRequestV1 updateCartItemMobileRequestV1);
    Boolean deleteCartItem(Long memberId, Long shopProductVariantId);
}
