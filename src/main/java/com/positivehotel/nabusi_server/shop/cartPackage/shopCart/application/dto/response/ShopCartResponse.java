package com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.dto.response;

import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ShopCartResponse {
    private Long id;
    private CartStatus status;
    private List<ShopCartItemResponse> shopCartItemList;
    private Integer totalPrice;
    private Integer totalDiscountPrice;
    private Integer totalAdditionalPrice;

    @Builder
    @Getter
    public static class ShopCartItemResponse {
        private Long shopProductId;
        private String shopProductName;
        private String modelName;
        private Integer price;
        private Integer discountPrice;
        private String listImage;
        private List<String> detailImage;
        private String soldOut;
        private ShopProductVariant shopProductVariant;
        private Integer quantity;

        @Builder
        @Getter
        public static class ShopProductVariant {
            private Long shopProductVariantId;
            private String optionName;
            private Integer additionalPrice;
            private Integer quantity;
        }
    }
}
