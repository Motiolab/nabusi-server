package com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="shop_product_variant")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductVariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shopProductId;
    @Column(nullable = false)
    private String optionName;
    private Integer additionalPrice;
    private String display;
    private String selling;
    private String displaySoldOut;
    private Integer quantity;
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    public void update(Long shopProductId, String optionName, Integer additionalPrice, String display, String selling, String displaySoldOut, Integer quantity) {
        this.shopProductId = shopProductId;
        this.optionName = optionName;
        this.additionalPrice = additionalPrice;
        this.display = display;
        this.selling = selling;
        this.displaySoldOut = displaySoldOut;
        this.quantity = quantity;
    }
}
