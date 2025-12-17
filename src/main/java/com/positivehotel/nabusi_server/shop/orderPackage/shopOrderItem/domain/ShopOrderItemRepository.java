package com.positivehotel.nabusi_server.shop.orderPackage.shopOrderItem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopOrderItemRepository extends JpaRepository<ShopOrderItemEntity, Long> {
    List<ShopOrderItemEntity> findAllByShopOrderIdIn(List<Long> shopOrderIdList);
    List<ShopOrderItemEntity> findAllByShopOrderId(Long shopOderId);
}
