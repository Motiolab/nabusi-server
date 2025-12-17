package com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "shop_order")
@Builder(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "purchase_confirmation", nullable = false)
    private Boolean purchaseConfirmation;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "total_discount_price", nullable = false)
    private Integer totalDiscountPrice;

    @Column(name = "total_additional_price", nullable = false)
    private Integer totalAdditionalPrice;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @Column(name = "receiver_address", nullable = false)
    private String receiverAddress;

    @Column(name = "receiver_address_code", nullable = false)
    private String receiverAddressCode;

    @Column(name = "receiver_detail_address", nullable = false)
    private String receiverDetailAddress;

    @Column(name = "used_point")
    private Long usedPoint;

    @Column(name = "reward_point")
    private Long rewardPoint;

    @LastModifiedDate
    @Column(name = "last_updated_date", nullable = false)
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public static ShopOrderEntity create(Long memberId, Long paymentId, Boolean purchaseConfirmation, String status,
            Integer totalPrice, Integer totalDiscountPrice, Integer totalAdditionalPrice, String receiverName,
            String receiverPhone, String receiverAddress, String receiverAddressCode, String receiverDetailAddress,
             Long usedPoint, Long rewardPoint) {
        return ShopOrderEntity.builder()
                .memberId(memberId)
                .paymentId(paymentId)
                .purchaseConfirmation(purchaseConfirmation)
                .status(status)
                .totalPrice(totalPrice)
                .totalDiscountPrice(totalDiscountPrice)
                .totalAdditionalPrice(totalAdditionalPrice)
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .receiverAddress(receiverAddress)
                .receiverAddressCode(receiverAddressCode)
                .receiverDetailAddress(receiverDetailAddress)
                .usedPoint(usedPoint)
                .rewardPoint(rewardPoint)
                .build();
    }
}