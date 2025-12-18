package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopOrderRepository extends JpaRepository<ShopOrderEntity, Long> {
    List<ShopOrderEntity> findAllByMemberId(Long memberId);
    Optional<ShopOrderEntity> findByIdAndMemberId(Long id, Long memberId);
}
