package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderMobileRequestV1 {
    private Long memberId;
    private Long shopCartId;
    private Long paymentId;
    private Boolean purchaseConfirmation;
    private String status;
    private Integer totalPrice;
    private Integer totalDiscountPrice;
    private Integer totalAdditionalPrice;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String receiverAddressCode;
    private String receiverDetailAddress;
    private Long usedPoint;
    private Long rewardPoint;
    private List<Item> itemList;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long shopOrderId;
        private Long shopProductVariantId;
        private Long shopProductId;
        private String optionName;
        private Integer additionalPrice;
        private String productName;
        private Integer price;
        private Integer productPrice;
        private String listImage;
        private String detailImage;
        private Integer quantity;
        private Integer totalPrice;
    }
}




