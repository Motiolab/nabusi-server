package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOrderMobileRequestV1 {
    private List<Item> itemList;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long shopProductVariantId;
        private Integer quantity;
    }
}




