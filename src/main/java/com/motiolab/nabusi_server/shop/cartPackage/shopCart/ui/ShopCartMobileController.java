package com.motiolab.nabusi_server.shop.cartPackage.shopCart.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.ShopCartMobileService;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartMobileDto;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.dto.response.ShopCartResponse;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShopCartMobileController {
        private final ShopCartMobileService shopCartMobileService;

        @GetMapping("/v1/mobile/cart/list")
        public ResponseEntity<ShopCartResponse> getCartByMemberIdAndStatus(
                        final @MemberId Long memberId,
                        final @RequestParam(required = false) CartStatus status) {
                final ShopCartMobileDto shopCartMobileDto = shopCartMobileService.getCartByMemberIdAndStatus(memberId, status);

                final List<ShopCartResponse.ShopCartItemResponse> shopCartItemList = shopCartMobileDto
                                .getShopCartItemExtensionList().stream()
                                .map(shopCartItemExtension -> ShopCartResponse.ShopCartItemResponse.builder()
                                                .shopProductId(shopCartItemExtension.getShopCartItemDto()
                                                                .getShopProductId())
                                                .shopProductName(shopCartItemExtension.getShopProductDto()
                                                                .getProductName())
                                                .modelName(shopCartItemExtension.getShopProductDto().getModelName())
                                                .price(shopCartItemExtension.getShopProductDto().getPrice())
                                                .discountPrice(shopCartItemExtension.getShopProductDto()
                                                                .getDiscountPrice())
                                                .listImage(shopCartItemExtension.getShopProductDto().getListImage())
                                                .detailImage(shopCartItemExtension.getShopProductDto().getDetailImage())
                                                .soldOut(shopCartItemExtension.getShopProductDto().getSoldOut())
                                                .shopProductVariant(
                                                                ShopCartResponse.ShopCartItemResponse.ShopProductVariant
                                                                                .builder()
                                                                                .shopProductVariantId(
                                                                                                shopCartItemExtension
                                                                                                                .getShopProductVariantDto()
                                                                                                                .getId())
                                                                                .optionName(shopCartItemExtension
                                                                                                .getShopProductVariantDto()
                                                                                                .getOptionName())
                                                                                .additionalPrice(shopCartItemExtension
                                                                                                .getShopProductVariantDto()
                                                                                                .getAdditionalPrice())
                                                                                .quantity(shopCartItemExtension
                                                                                                .getShopProductVariantDto()
                                                                                                .getQuantity())
                                                                                .build())
                                                .quantity(shopCartItemExtension.getShopCartItemDto().getQuantity())
                                                .build())
                                .toList();

                final ShopCartResponse shopCartResponse = ShopCartResponse.builder()
                                .id(shopCartMobileDto.getShopCartDto().getId())
                                .status(shopCartMobileDto.getShopCartDto().getStatus())
                                .shopCartItemList(shopCartItemList)
                                .build();

                return ResponseEntity.ok(shopCartResponse);
        }
}