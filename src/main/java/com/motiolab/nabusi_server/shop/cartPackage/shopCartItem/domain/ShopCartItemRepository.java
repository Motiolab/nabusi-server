package com.motiolab.nabusi_server.shop.cartPackage.shopCartItem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopCartItemRepository extends JpaRepository<ShopCartItemEntity, Long> {
    Optional<ShopCartItemEntity> findByShopCartIdAndShopProductVariantId(Long shopCartId, Long shopProductVariantId);
    List<ShopCartItemEntity> findAllByShopCartId(Long shopCartId);
}
