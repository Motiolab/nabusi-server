package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelShopOrderMobileRequestV1 {
    private Long shopOrderId;
    private String cancelReason;
}
