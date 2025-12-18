package com.motiolab.nabusi_server.shop.productPackage.shopProduct.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopProductRepository extends JpaRepository<ShopProductEntity, Long> {
    List<ShopProductEntity> findAllByIdIn(List<Long> idList);
}
