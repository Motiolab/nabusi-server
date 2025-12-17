package com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.ShopCartService;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartDto;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto.ShopCartMobileDto;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.ShopCartItemDto;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.request.CreateCartItemMobileRequestV1;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.request.UpdateCartItemMobileRequestV1;
import com.positivehotel.nabusi_server.shop.productPackage.shopProduct.application.ShopProductService;
import com.positivehotel.nabusi_server.shop.productPackage.shopProduct.application.dto.ShopProductDto;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.application.ShopProductVariantService;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCartItemMobileServiceImpl implements ShopCartItemMobileService {
    private final ShopCartService shopCartService;
    private final ShopCartItemService shopCartItemService;
    private final ShopProductService shopProductService;
    private final ShopProductVariantService shopProductVariantService;

    @Override
    public ShopCartMobileDto createCartItem(CreateCartItemMobileRequestV1 createCartItemMobileRequestV1) {
        ShopCartDto shopCartDto = shopCartService.getByMemberIdAndStatus(createCartItemMobileRequestV1.getMemberId(), CartStatus.BEFORE_ORDER);
        if (shopCartDto == null) {
            final ShopCartDto createShopCartDto = ShopCartDto.builder().memberId(createCartItemMobileRequestV1.getMemberId()).status(CartStatus.BEFORE_ORDER).build();
            shopCartDto = shopCartService.create(createShopCartDto);
        }

        final ShopCartItemDto shopCartItemDto = shopCartItemService.getByShopCartIdAndShopProductVariantId(shopCartDto.getId(), createCartItemMobileRequestV1.getShopProductVariantId());
        if (shopCartItemDto == null) {
            final ShopCartItemDto createShopCartItemDto = ShopCartItemDto.builder()
                    .shopCartId(shopCartDto.getId())
                    .shopProductId(createCartItemMobileRequestV1.getShopProductId())
                    .shopProductVariantId(createCartItemMobileRequestV1.getShopProductVariantId())
                    .quantity(createCartItemMobileRequestV1.getQuantity())
                    .build();
            shopCartItemService.create(createShopCartItemDto);
        }else{
            if(createCartItemMobileRequestV1.getQuantity() == 0) {
                shopCartItemService.deleteById(shopCartItemDto.getId());
            }else {
                final ShopProductVariantDto shopProductVariantDto = shopProductVariantService.getById(createCartItemMobileRequestV1.getShopProductVariantId());
                int newQuantity = shopCartItemDto.getQuantity() + createCartItemMobileRequestV1.getQuantity();
                if(newQuantity >= shopProductVariantDto.getQuantity()) {
                    newQuantity = shopProductVariantDto.getQuantity();
                }
                shopCartItemDto.setShopProductId(createCartItemMobileRequestV1.getShopProductId());
                shopCartItemDto.setQuantity(newQuantity);
                shopCartItemDto.setShopProductVariantId(createCartItemMobileRequestV1.getShopProductVariantId());
                shopCartItemService.update(shopCartItemDto);
            }
        }

        final List<ShopCartItemDto> shopCartItemDtoList = shopCartItemService.getAllShopCartId(shopCartDto.getId());
        final List<Long> shopProductIdList = shopCartItemDtoList.stream().map(ShopCartItemDto::getShopProductId).distinct().toList();
        final List<ShopProductDto> shopProductDtoList = shopProductService.getAllByIdList(shopProductIdList);

        final List<Long> shopProductVariantIdList = shopCartItemDtoList.stream().map(ShopCartItemDto::getShopProductVariantId).distinct().toList();
        final List<ShopProductVariantDto> shopProductVariantDtoList = shopProductVariantService.getAllByIdList(shopProductVariantIdList);

        final List<ShopCartMobileDto.ShopCartItemExtension> shopCartItemExtensionList = shopCartItemDtoList.stream().map(shopCartItemDto1 -> {
            final ShopProductDto shopProductDto = shopProductDtoList.stream().filter(shopProductDto1 -> shopProductDto1.getId().equals(shopCartItemDto1.getShopProductId())).findFirst().orElse(null);
            final ShopProductVariantDto shopProductVariantDto = shopProductVariantDtoList.stream().filter(shopProductVariantDto1 -> shopProductVariantDto1.getId().equals(shopCartItemDto1.getShopProductVariantId())).findFirst().orElse(null);
            return ShopCartMobileDto.ShopCartItemExtension.builder()
                    .shopCartItemDto(shopCartItemDto1)
                    .shopProductDto(shopProductDto)
                    .shopProductVariantDto(shopProductVariantDto)
                    .build();
        }).toList();

        return ShopCartMobileDto.builder()
                .shopCartDto(shopCartDto)
                .shopCartItemExtensionList(shopCartItemExtensionList)
                .build();
    }

    @Override
    public Boolean updateCartItem(UpdateCartItemMobileRequestV1 updateCartItemMobileRequestV1) {
        ShopCartDto shopCartDto = shopCartService.getByMemberIdAndStatus(updateCartItemMobileRequestV1.getMemberId(), CartStatus.BEFORE_ORDER);
        if (shopCartDto == null) {
            final ShopCartDto createShopCartDto = ShopCartDto.builder().memberId(updateCartItemMobileRequestV1.getMemberId()).status(CartStatus.BEFORE_ORDER).build();
            shopCartDto = shopCartService.create(createShopCartDto);
        }

        final ShopCartItemDto shopCartItemDto = shopCartItemService.getByShopCartIdAndShopProductVariantId(shopCartDto.getId(), updateCartItemMobileRequestV1.getShopProductVariantId());
        if (shopCartItemDto != null && updateCartItemMobileRequestV1.getQuantity() > 0) {
            shopCartItemDto.setQuantity(updateCartItemMobileRequestV1.getQuantity());
            shopCartItemService.update(shopCartItemDto);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteCartItem(Long memberId, Long shopProductVariantId) {
        ShopCartDto shopCartDto = shopCartService.getByMemberIdAndStatus(memberId, CartStatus.BEFORE_ORDER);
        if (shopCartDto == null) {
            final ShopCartDto createShopCartDto = ShopCartDto.builder().memberId(memberId).status(CartStatus.BEFORE_ORDER).build();
            shopCartDto = shopCartService.create(createShopCartDto);
        }

        final ShopCartItemDto shopCartItemDto = shopCartItemService.getByShopCartIdAndShopProductVariantId(shopCartDto.getId(), shopProductVariantId);
        if(shopCartItemDto != null) {
            shopCartItemService.deleteById(shopCartItemDto.getId());
            return true;
        }else {
            return false;
        }
    }
}
