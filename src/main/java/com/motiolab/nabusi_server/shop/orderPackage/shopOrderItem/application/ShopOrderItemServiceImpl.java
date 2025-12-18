package com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application;

import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application.dto.ShopOrderItemDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.domain.ShopOrderItemEntity;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.domain.ShopOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopOrderItemServiceImpl implements ShopOrderItemService{
    private final ShopOrderItemRepository shopOrderItemRepository;

    @Override
    public List<ShopOrderItemDto> saveAll(List<ShopOrderItemDto> shopOrderItemDtoList) {
        final List<ShopOrderItemEntity> shopOrderItemEntityList = shopOrderItemDtoList.stream().map(shopOrderItemDto ->
                ShopOrderItemEntity.create(
                        shopOrderItemDto.getShopOrderId(),
                        shopOrderItemDto.getShopProductVariantId(),
                        shopOrderItemDto.getShopProductId(),
                        shopOrderItemDto.getOptionName(),
                        shopOrderItemDto.getAdditionalPrice(),
                        shopOrderItemDto.getProductName(),
                        shopOrderItemDto.getPrice(),
                        shopOrderItemDto.getProductPrice(),
                        shopOrderItemDto.getListImage(),
                        shopOrderItemDto.getDetailImage(),
                        shopOrderItemDto.getQuantity(),
                        shopOrderItemDto.getTotalPrice()
                )).toList();

        final List<ShopOrderItemEntity> storedShopOrderItemEntityList = shopOrderItemRepository.saveAll(shopOrderItemEntityList);
        return storedShopOrderItemEntityList.stream().map(ShopOrderItemDto::from).toList();
    }

    @Override
    public List<ShopOrderItemDto> getAllByShopOrderIdList(List<Long> shopOrderIdList) {
        List<ShopOrderItemEntity> shopOrderItemEntityList = shopOrderItemRepository.findAllByShopOrderIdIn(shopOrderIdList);
        return shopOrderItemEntityList.stream().map(ShopOrderItemDto::from).toList();
    }

    @Override
    public List<ShopOrderItemDto> getAllByShopOrderId(Long shopOrderId) {
        List<ShopOrderItemEntity> shopOrderItemEntityList = shopOrderItemRepository.findAllByShopOrderId(shopOrderId);
        return shopOrderItemEntityList.stream().map(ShopOrderItemDto::from).toList();
    }
}
