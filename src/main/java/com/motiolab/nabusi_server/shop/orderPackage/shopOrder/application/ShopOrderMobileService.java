package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application;

import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderMobileDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CancelShopOrderMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CreateOrderWithPaymentConfirmMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.ValidateOrderMobileRequestV1;

import java.util.List;

public interface ShopOrderMobileService {
    ShopOrderMobileDto createOrderWithPaymentConfirm(
            CreateOrderWithPaymentConfirmMobileRequestV1 createOrderWithPaymentConfirmMobileRequestV1);

    List<ShopOrderMobileDto> getOrderListByMemberId(Long memberId);

    ShopOrderMobileDto getOrderByIdAndMemberId(Long id, Long memberId);

    void validateOrder(ValidateOrderMobileRequestV1 validateOrderMobileRequestV1);

    void cancelOrder(CancelShopOrderMobileRequestV1 cancelShopOrderMobileRequestV1);
}
