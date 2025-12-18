package com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application;

import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application.dto.ShopOrderItemDto;

import java.util.List;

public interface ShopOrderItemService {
    List<ShopOrderItemDto> saveAll(List<ShopOrderItemDto> shopOrderItemDtoList);
    List<ShopOrderItemDto> getAllByShopOrderIdList(List<Long> shopOrderIdList);
    List<ShopOrderItemDto> getAllByShopOrderId(Long shopOrderId);
}