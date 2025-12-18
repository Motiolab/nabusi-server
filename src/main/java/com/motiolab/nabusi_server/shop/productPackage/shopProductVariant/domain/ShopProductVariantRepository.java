package com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopProductVariantRepository extends JpaRepository<ShopProductVariantEntity, Long> {
    List<ShopProductVariantEntity> findAllByShopProductId(Long shopProductId);
    List<ShopProductVariantEntity> findAllByIdIn(List<Long> idList);
}
