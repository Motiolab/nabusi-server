package com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application;

import com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderDto;

import java.util.List;

public interface ShopOrderService {
    ShopOrderDto save(ShopOrderDto shopOrderDto);
    List<ShopOrderDto> getAllByMemberId(Long memberId);
    ShopOrderDto getByIdAndMemberId(Long id, Long memberId);
}
