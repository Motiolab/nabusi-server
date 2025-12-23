package com.motiolab.nabusi_server.shop.cartPackage.shopCart.application;

import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartDto;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartMobileDto;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;
import com.motiolab.nabusi_server.shop.cartPackage.shopCartItem.application.ShopCartItemService;
import com.motiolab.nabusi_server.shop.cartPackage.shopCartItem.application.dto.ShopCartItemDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.ShopProductService;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.ShopProductVariantService;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCartMobileServiceImpl implements ShopCartMobileService {
        private final ShopCartService shopCartService;
        private final ShopCartItemService shopCartItemService;
        private final ShopProductService shopProductService;
        private final ShopProductVariantService shopProductVariantService;

        @Override
        public ShopCartMobileDto getCartByMemberIdAndStatus(Long memberId, CartStatus status) {
                if (status == null) {
                        status = CartStatus.BEFORE_ORDER;
                }
                ShopCartDto shopCartDto = shopCartService.getByMemberIdAndStatus(memberId, status);
                if (shopCartDto == null) {
                        final ShopCartDto createShopCartDto = ShopCartDto.builder().memberId(memberId)
                                        .status(CartStatus.BEFORE_ORDER).build();
                        shopCartDto = shopCartService.create(createShopCartDto);
                        return ShopCartMobileDto.builder().shopCartDto(shopCartDto).build();
                }
                final List<ShopCartItemDto> shopCartItemDtoList = shopCartItemService
                                .getAllShopCartId(shopCartDto.getId());

                final List<Long> shopProductIdList = shopCartItemDtoList.stream().map(ShopCartItemDto::getShopProductId)
                                .toList();
                final List<ShopProductDto> shopProductDtoList = shopProductService.getAllByIdList(shopProductIdList);
                final List<Long> shopProductVariantIdList = shopCartItemDtoList.stream()
                                .map(ShopCartItemDto::getShopProductVariantId).toList();
                final List<ShopProductVariantDto> shopProductVariantDtoList = shopProductVariantService
                                .getAllByIdList(shopProductVariantIdList);

                final List<ShopCartMobileDto.ShopCartItemExtension> shopCartItemExtensionList = shopCartItemDtoList
                                .stream()
                                .map(shopCartItemDto -> {
                                        ShopProductDto shopProductDto = shopProductDtoList.stream().filter(
                                                        shopProductDto1 -> shopProductDto1.getId()
                                                                        .equals(shopCartItemDto.getShopProductId()))
                                                        .findFirst().orElse(null);
                                        ShopProductVariantDto shopProductVariantDto = shopProductVariantDtoList.stream()
                                                        .filter(shopProductVariantDto1 -> shopProductVariantDto1.getId()
                                                                        .equals(shopCartItemDto
                                                                                        .getShopProductVariantId()))
                                                        .findFirst().orElse(null);

                                        return ShopCartMobileDto.ShopCartItemExtension.builder()
                                                        .shopCartItemDto(shopCartItemDto)
                                                        .shopProductDto(shopProductDto)
                                                        .shopProductVariantDto(shopProductVariantDto)
                                                        .build();
                                })
                                .filter(extension -> extension.getShopProductDto() != null
                                                && extension.getShopProductVariantDto() != null)
                                .toList();

                return ShopCartMobileDto.builder()
                                .shopCartDto(shopCartDto)
                                .shopCartItemExtensionList(shopCartItemExtensionList)
                                .build();
        }
}
