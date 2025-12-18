package com.motiolab.nabusi_server.shop.productPackage.shopProductReview.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopProductReviewRepository extends JpaRepository<ShopProductReviewEntity, Long> {
    @Query(value = "SELECT spr FROM shop_product_review spr " +
            "WHERE spr.shop_product_id = :shopProductId " +
            "ORDER BY " +
            "  CASE WHEN spr.best = true THEN 0 ELSE 1 END, " +
            "  CASE WHEN spr.idx IS NULL THEN 1 ELSE 0 END, " +
            "  spr.idx ASC", nativeQuery = true)
    List<ShopProductReviewEntity> findAllOrderByBestAndIdx(Long shopProductId);

    List<ShopProductReviewEntity> findAllByShopProductIdIn(List<Long> shopProductIdList);
}
