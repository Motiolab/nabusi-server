package com.positivehotel.nabusi_server.shop.orderPackage.shopOrderItem.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name="shop_order_item")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_order_id", nullable = false)
    private Long shopOrderId;

    @Column(name = "shop_product_variant_id", nullable = false)
    private Long shopProductVariantId;

    @Column(name = "shop_product_id", nullable = false)
    private Long shopProductId;

    @Column(name = "option_name", nullable = false)
    private String optionName;

    @Column(name = "additional_price", nullable = false)
    private Integer additionalPrice;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "list_image")
    private String listImage;

    @Column(name = "detail_image")
    private String detailImage;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    public static ShopOrderItemEntity create(Long shopOrderId, Long shopProductVariantId, Long shopProductId, String optionName, Integer additionalPrice, String productName, Integer price, Integer productPrice, String listImage, String detailImage, Integer quantity, Integer totalPrice) {
        return ShopOrderItemEntity.builder()
                .shopOrderId(shopOrderId)
                .shopProductVariantId(shopProductVariantId)
                .shopProductId(shopProductId)
                .optionName(optionName)
                .additionalPrice(additionalPrice)
                .productName(productName)
                .price(price)
                .productPrice(productPrice)
                .listImage(listImage)
                .detailImage(detailImage)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }
}