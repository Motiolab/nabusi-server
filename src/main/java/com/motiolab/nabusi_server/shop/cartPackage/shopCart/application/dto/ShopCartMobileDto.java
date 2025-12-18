package com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto;

import com.motiolab.nabusi_server.shop.cartPackage.shopCartItem.application.dto.ShopCartItemDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartMobileDto {
    ShopCartDto shopCartDto;
    List<ShopCartItemExtension> shopCartItemExtensionList;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShopCartItemExtension {
        ShopCartItemDto shopCartItemDto;
        ShopProductDto shopProductDto;
        ShopProductVariantDto shopProductVariantDto;
    }
}
