package com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.application.dto.ShopCartItemDto;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.domain.ShopCartItemEntity;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.domain.ShopCartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCartItemServiceImpl implements ShopCartItemService{
    private final ShopCartItemRepository shopCartItemRepository;

    @Override
    public ShopCartItemDto getByShopCartIdAndShopProductVariantId(Long shopCartId, Long shopProductVariantId) {
        return shopCartItemRepository.findByShopCartIdAndShopProductVariantId(shopCartId, shopProductVariantId)
                .map(ShopCartItemDto::from)
                .orElse(null);
    }

    @Override
    public ShopCartItemDto create(ShopCartItemDto shopCartItemDto) {
        final ShopCartItemEntity shopCartItemEntity = ShopCartItemEntity.create(
                shopCartItemDto.getShopCartId(),
                shopCartItemDto.getShopProductId(),
                shopCartItemDto.getShopProductVariantId(),
                shopCartItemDto.getQuantity()
        );

        final ShopCartItemEntity storedShopCartItemEntity = shopCartItemRepository.save(shopCartItemEntity);
        return ShopCartItemDto.from(storedShopCartItemEntity);
    }

    @Override
    public List<ShopCartItemDto> getAllShopCartId(Long shopCartId) {
        List<ShopCartItemEntity> shopCartItemEntityList = shopCartItemRepository.findAllByShopCartId(shopCartId);
        return shopCartItemEntityList.stream().map(ShopCartItemDto::from).toList();
    }

    @Override
    public ShopCartItemDto update(ShopCartItemDto shopCartItemDto) {
        return shopCartItemRepository.findById(shopCartItemDto.getId())
                .map(shopCartItemEntity -> {
                    shopCartItemEntity.update(
                            shopCartItemDto.getShopCartId(),
                            shopCartItemDto.getShopProductId(),
                            shopCartItemDto.getShopProductVariantId(),
                            shopCartItemDto.getQuantity()
                    );
                    return ShopCartItemDto.from(shopCartItemRepository.save(shopCartItemEntity));
                })
                .orElseThrow(() -> new IllegalArgumentException("Center not found with id: " + shopCartItemDto.getId()));
    }

    @Override
    public void deleteById(Long id) {
        shopCartItemRepository.deleteById(id);
    }
}
