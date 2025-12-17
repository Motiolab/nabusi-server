package com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.ui;

import com.positivehotel.nabusi_server.argumentResolver.MemberId;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartMobileDto;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto.response.ShopCartResponse;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.ShopCartItemMobileService;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.request.CreateCartItemMobileRequestV1;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.request.UpdateCartItemMobileRequestV1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShopCartItemMobileController {
    private final ShopCartItemMobileService shopCartItemMobileService;

    @PostMapping("/v1/mobile/cart/item")
    public ResponseEntity<ShopCartResponse> createCartItem(final @MemberId Long memberId, final @RequestBody CreateCartItemMobileRequestV1 createCartItemMobileRequestV1){
        createCartItemMobileRequestV1.setMemberId(memberId);
        final ShopCartMobileDto shopCartMobileDto = shopCartItemMobileService.createCartItem(createCartItemMobileRequestV1);

        final List<ShopCartResponse.ShopCartItemResponse> shopCartItemList = shopCartMobileDto.getShopCartItemExtensionList().stream()
                .map(shopCartItemExtension -> ShopCartResponse.ShopCartItemResponse.builder()
                        .shopProductId(shopCartItemExtension.getShopCartItemDto().getShopProductId())
                        .shopProductName(shopCartItemExtension.getShopProductDto().getProductName())
                        .modelName(shopCartItemExtension.getShopProductDto().getModelName())
                        .price(shopCartItemExtension.getShopProductDto().getPrice())
                        .discountPrice(shopCartItemExtension.getShopProductDto().getDiscountPrice())
                        .listImage(shopCartItemExtension.getShopProductDto().getListImage())
                        .detailImage(shopCartItemExtension.getShopProductDto().getDetailImage())
                        .soldOut(shopCartItemExtension.getShopProductDto().getSoldOut())
                        .shopProductVariant(ShopCartResponse.ShopCartItemResponse.ShopProductVariant.builder()
                                .shopProductVariantId(shopCartItemExtension.getShopProductVariantDto().getId())
                                .optionName(shopCartItemExtension.getShopProductVariantDto().getOptionName())
                                .additionalPrice(shopCartItemExtension.getShopProductVariantDto().getAdditionalPrice())
                                .build())
                        .quantity(shopCartItemExtension.getShopCartItemDto().getQuantity())
                        .build())
                .toList();

        final Integer totalPrice = shopCartMobileDto.getShopCartItemExtensionList().stream()
                .mapToInt(shopCartItemExtension -> shopCartItemExtension.getShopProductDto().getPrice() * shopCartItemExtension.getShopCartItemDto().getQuantity())
                .sum();

        final Integer totalDiscountPrice = shopCartMobileDto.getShopCartItemExtensionList().stream()
                .mapToInt(shopCartItemExtension -> shopCartItemExtension.getShopProductDto().getDiscountPrice() * shopCartItemExtension.getShopCartItemDto().getQuantity())
                .sum();

        final Integer totalAdditionalPrice = shopCartMobileDto.getShopCartItemExtensionList().stream()
                .mapToInt(shopCartItemExtension -> shopCartItemExtension.getShopProductVariantDto().getAdditionalPrice() * shopCartItemExtension.getShopCartItemDto().getQuantity())
                .sum();

        final ShopCartResponse shopCartResponse = ShopCartResponse.builder()
                .id(shopCartMobileDto.getShopCartDto().getId())
                .status(shopCartMobileDto.getShopCartDto().getStatus())
                .shopCartItemList(shopCartItemList)
                .totalPrice(totalPrice)
                .totalDiscountPrice(totalDiscountPrice)
                .totalAdditionalPrice(totalAdditionalPrice)
                .build();

        return ResponseEntity.ok(shopCartResponse);
    }

    @PutMapping("/v1/mobile/cart/item")
    public ResponseEntity<Boolean> updateCartItem(final @MemberId Long memberId, @RequestBody UpdateCartItemMobileRequestV1 updateCartItemMobileRequestV1) {
        updateCartItemMobileRequestV1.setMemberId(memberId);
        final Boolean result = shopCartItemMobileService.updateCartItem(updateCartItemMobileRequestV1);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/mobile/cart/item/{shopProductVariantId}")
    public ResponseEntity<Boolean> deleteCartItem(final @MemberId Long memberId, @PathVariable Long shopProductVariantId) {
        final Boolean result = shopCartItemMobileService.deleteCartItem(memberId, shopProductVariantId);
        return ResponseEntity.ok(result);
    }
}
