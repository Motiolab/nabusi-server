package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class GetShopOrderListByMemberIdMobileResponse {
    ShopOrderResponse shopOrderResponse;
    List<ShopOrderItemResponse> shopOrderItemResponseList;

    @Builder
    @Getter
    public static class ShopOrderResponse{
        private Long id;
        private Long memberId;
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
        private LocalDateTime lastUpdatedDate;
        private LocalDateTime createdDate;
    }

    @Builder
    @Getter
    public static class ShopOrderItemResponse{
        private Long id;
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
