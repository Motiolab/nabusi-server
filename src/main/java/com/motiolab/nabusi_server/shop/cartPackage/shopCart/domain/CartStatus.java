package com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain;

public enum CartStatus {
    BEFORE_ORDER("주문전"),
    COMPLETE_ORDER("주문완료");

    private final String description;

    CartStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}