package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application;

import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.domain.ShopOrderEntity;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.domain.ShopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopOrderServiceImpl implements ShopOrderService {
    private final ShopOrderRepository shopOrderRepository;

    @Override
    public ShopOrderDto save(ShopOrderDto shopOrderDto) {
        final ShopOrderEntity shopOrderEntity = ShopOrderEntity.create(
                shopOrderDto.getMemberId(),
                shopOrderDto.getPaymentId(),
                shopOrderDto.getPurchaseConfirmation(),
                shopOrderDto.getStatus(),
                shopOrderDto.getTotalPrice(),
                shopOrderDto.getTotalDiscountPrice(),
                shopOrderDto.getTotalAdditionalPrice(),
                shopOrderDto.getReceiverName(),
                shopOrderDto.getReceiverPhone(),
                shopOrderDto.getReceiverAddress(),
                shopOrderDto.getReceiverAddressCode(),
                shopOrderDto.getReceiverDetailAddress(),
                shopOrderDto.getUsedPoint(),
                shopOrderDto.getRewardPoint()
        );

        final ShopOrderEntity storedShopOrderEntity = shopOrderRepository.save(shopOrderEntity);
        return ShopOrderDto.from(storedShopOrderEntity);
    }

    @Override
    public List<ShopOrderDto> getAllByMemberId(Long memberId) {
        final List<ShopOrderEntity> shopOrderEntityList = shopOrderRepository.findAllByMemberId(memberId);
        return shopOrderEntityList.stream().map(ShopOrderDto::from).toList();
    }

    @Override
    public ShopOrderDto getByIdAndMemberId(Long id, Long memberId) {
        return shopOrderRepository.findByIdAndMemberId(id, memberId)
                .map(ShopOrderDto::from)
                .orElse(null);
    }
}
