package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto;

import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application.dto.ShopOrderItemDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ShopOrderMobileDto {
    ShopOrderDto shopOrderDto;
    List<ShopOrderItemDto> shopOrderItemDto;
}
