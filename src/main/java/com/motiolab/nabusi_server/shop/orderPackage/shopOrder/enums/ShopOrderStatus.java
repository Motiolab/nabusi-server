package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShopOrderStatus {
    ORDER_CONFIRMATION_PENDING("주문 확인중"),
    PREPARING_SHIPMENT("상품 준비중"),
    SHIPPING("배송중"),
    DELIVERY_COMPLETE("배송 완료"),
    PURCHASE_CONFIRMED("구매 확정"),
    CANCELED("주문 취소"),
    REFUNDED("환불 완료");

    private final String description;
}
