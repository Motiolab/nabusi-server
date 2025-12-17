package com.positivehotel.nabusi_server.shop.cartPackage.shopCart.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopCartRepository extends JpaRepository<ShopCartEntity, Long> {
    Optional<ShopCartEntity> findByMemberIdAndStatus(Long memberId, CartStatus cartStatus);
}
