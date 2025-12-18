package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application;

import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderMobileDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CreateOrderMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.ValidateOrderMobileRequestV1;

import java.util.List;

public interface ShopOrderMobileService {
    ShopOrderMobileDto create(CreateOrderMobileRequestV1 createOrderMobileRequestV1);
    List<ShopOrderMobileDto> getOrderListByMemberId(Long memberId);
    ShopOrderMobileDto getOrderByIdAndMemberId(Long id, Long memberId);
    void validateOrder(ValidateOrderMobileRequestV1 validateOrderMobileRequestV1);
}
