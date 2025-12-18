package com.motiolab.nabusi_server.shop.cartPackage.shopCartItem.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateCartItemMobileRequestV1 {
    Long memberId;
    Long shopProductId;
    Integer quantity;
    Long shopProductVariantId;
}
