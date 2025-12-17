package com.positivehotel.nabusi_server.shop.cartPackage.shopCartItem.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="shop_cart_item")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_cart_id", nullable = false)
    private Long shopCartId;

    @Column(name = "shop_product_id", nullable = false)
    private Long shopProductId;

    @Column(name = "shop_product_variant_id", nullable = false)
    private Long shopProductVariantId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @LastModifiedDate
    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public static ShopCartItemEntity create(Long shopCartId, Long shopProductId, Long shopProductVariantId, Integer quantity) {
        return ShopCartItemEntity.builder()
                .shopCartId(shopCartId)
                .shopProductId(shopProductId)
                .shopProductVariantId(shopProductVariantId)
                .quantity(quantity)
                .build();
    }

    public void update(Long shopCartId, Long shopProductId, Long shopProductVariantId, Integer quantity) {
        if(shopCartId != null) this.setShopCartId(shopCartId);
        if(shopProductId != null) this.setShopProductId(shopProductId);
        if(shopProductVariantId != null) this.setShopProductVariantId(shopProductVariantId);
        if(quantity != null) this.setQuantity(quantity);
    }
}