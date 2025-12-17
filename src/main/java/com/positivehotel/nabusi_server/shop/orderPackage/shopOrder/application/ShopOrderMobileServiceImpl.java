package com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application;

import com.positivehotel.nabusi_server.memberPackage.memberPoint.application.MemberPointService;
import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.application.MemberPointHistoryService;
import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.application.dto.MemberPointHistoryDto;
import com.positivehotel.nabusi_server.memberPackage.memberPointHistory.domain.PointTransactionType;
import com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderDto;
import com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderMobileDto;
import com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CreateOrderMobileRequestV1;
import com.positivehotel.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.ValidateOrderMobileRequestV1;
import com.positivehotel.nabusi_server.shop.orderPackage.shopOrderItem.application.ShopOrderItemService;
import com.positivehotel.nabusi_server.shop.orderPackage.shopOrderItem.application.dto.ShopOrderItemDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import com.positivehotel.nabusi_server.exception.customException.InsufficientStockException;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.application.ShopProductVariantService;
import com.positivehotel.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import com.positivehotel.nabusi_server.shop.productPackage.shopProduct.application.ShopProductService;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.application.ShopCartService;
import com.positivehotel.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;

@Service
@RequiredArgsConstructor
public class ShopOrderMobileServiceImpl implements ShopOrderMobileService {
        private final ShopOrderService shopOrderService;
        private final ShopOrderItemService shopOrderItemService;
        private final MemberPointService memberPointService;
        private final MemberPointHistoryService memberPointHistoryService;
        private final ShopProductVariantService shopProductVariantService;
        private final ShopProductService shopProductService;
        private final ShopCartService shopCartService;

        @Transactional
        @Override
        public ShopOrderMobileDto create(CreateOrderMobileRequestV1 createOrderMobileRequestV1) {
                final List<ShopProductVariantDto> shopProductVariantDtoList = shopProductVariantService.getAllByIdList(
                                createOrderMobileRequestV1.getItemList().stream()
                                                .map(CreateOrderMobileRequestV1.Item::getShopProductVariantId)
                                                .toList());

                if (shopProductVariantDtoList.size() != createOrderMobileRequestV1.getItemList().size()) {
                        throw new InsufficientStockException("재고가 부족합니다.");
                }

                final List<ShopProductVariantDto> updatedShopProductVariantDtoList = shopProductVariantDtoList.stream()
                                .map(it -> shopProductVariantService.update(ShopProductVariantDto.builder()
                                                .id(it.getId())
                                                .shopProductId(it.getShopProductId())
                                                .optionName(it.getOptionName())
                                                .additionalPrice(it.getAdditionalPrice())
                                                .display(it.getDisplay())
                                                .selling(it.getSelling())
                                                .displaySoldOut(it.getDisplaySoldOut())
                                                .quantity(it.getQuantity() - createOrderMobileRequestV1.getItemList()
                                                                .stream()
                                                                .filter(item -> item.getShopProductVariantId()
                                                                                .equals(it.getId()))
                                                                .findFirst()
                                                                .orElseThrow(() -> new InsufficientStockException(
                                                                                "재고가 부족합니다."))
                                                                .getQuantity())
                                                .build()))
                                .toList();

                updatedShopProductVariantDtoList.stream()
                                .map(ShopProductVariantDto::getShopProductId)
                                .distinct()
                                .forEach(shopProductId -> {
                                        List<ShopProductVariantDto> variants = shopProductVariantService
                                                        .getAllByShopProductId(shopProductId);
                                        int totalQuantity = variants.stream()
                                                        .mapToInt(ShopProductVariantDto::getQuantity)
                                                        .sum();
                                        if (totalQuantity == 0) {
                                                shopProductService.updateSoldOut(shopProductId, "true");
                                        }
                                });

                final ShopOrderDto shopOrderDto = ShopOrderDto.builder()
                                .memberId(createOrderMobileRequestV1.getMemberId())
                                .paymentId(createOrderMobileRequestV1.getPaymentId())
                                .purchaseConfirmation(createOrderMobileRequestV1.getPurchaseConfirmation())
                                .status(createOrderMobileRequestV1.getStatus())
                                .totalPrice(createOrderMobileRequestV1.getTotalPrice())
                                .totalDiscountPrice(createOrderMobileRequestV1.getTotalDiscountPrice())
                                .totalAdditionalPrice(createOrderMobileRequestV1.getTotalAdditionalPrice())
                                .receiverName(createOrderMobileRequestV1.getReceiverName())
                                .receiverPhone(createOrderMobileRequestV1.getReceiverPhone())
                                .receiverAddress(createOrderMobileRequestV1.getReceiverAddress())
                                .receiverAddressCode(createOrderMobileRequestV1.getReceiverAddressCode())
                                .receiverDetailAddress(createOrderMobileRequestV1.getReceiverDetailAddress())
                                .build();

                final ShopOrderDto storedShopOrderDto = shopOrderService.save(shopOrderDto);
                final List<ShopOrderItemDto> shopOrderItemDtoList = createOrderMobileRequestV1.getItemList()
                                .stream()
                                .map(item -> ShopOrderItemDto.builder()
                                                .shopOrderId(storedShopOrderDto.getId())
                                                .shopProductVariantId(item.getShopProductVariantId())
                                                .shopProductId(item.getShopProductId())
                                                .optionName(item.getOptionName())
                                                .additionalPrice(item.getAdditionalPrice())
                                                .productName(item.getProductName())
                                                .price(item.getPrice())
                                                .productPrice(item.getProductPrice())
                                                .listImage(item.getListImage())
                                                .detailImage(item.getDetailImage())
                                                .quantity(item.getQuantity())
                                                .totalPrice(item.getTotalPrice())
                                                .build())
                                .toList();

                final List<ShopOrderItemDto> storedShopOrderItemDtoList = shopOrderItemService
                                .saveAll(shopOrderItemDtoList);

                if (createOrderMobileRequestV1.getShopCartId() != null) {
                        shopCartService.updateStatus(createOrderMobileRequestV1.getShopCartId(),
                                        CartStatus.COMPLETE_ORDER);
                }

                if (createOrderMobileRequestV1.getUsedPoint() > 0) {
                        memberPointService.addPoint(createOrderMobileRequestV1.getMemberId(),
                                        createOrderMobileRequestV1.getUsedPoint());
                        memberPointHistoryService.create(MemberPointHistoryDto.builder()
                                        .memberId(createOrderMobileRequestV1.getMemberId())
                                        .amount(createOrderMobileRequestV1.getUsedPoint())
                                        .transactionType(PointTransactionType.USE)
                                        .description("주문 시 포인트 사용")
                                        .referenceId("paymentID")
                                        .build());
                } else if (createOrderMobileRequestV1.getRewardPoint() > 0) {
                        memberPointService.addPoint(createOrderMobileRequestV1.getMemberId(),
                                        createOrderMobileRequestV1.getRewardPoint());
                        memberPointHistoryService.create(MemberPointHistoryDto.builder()
                                        .memberId(createOrderMobileRequestV1.getMemberId())
                                        .amount(createOrderMobileRequestV1.getRewardPoint())
                                        .transactionType(PointTransactionType.EARN)
                                        .description("주문 시 포인트 적립")
                                        .referenceId("paymentID")
                                        .build());
                }

                return ShopOrderMobileDto.builder()
                                .shopOrderDto(storedShopOrderDto)
                                .shopOrderItemDto(storedShopOrderItemDtoList)
                                .build();
        }

        @Override
        public List<ShopOrderMobileDto> getOrderListByMemberId(Long memberId) {
                final List<ShopOrderDto> shopOrderDtoList = shopOrderService.getAllByMemberId(memberId);
                final List<Long> shopOrderIdList = shopOrderDtoList.stream().map(ShopOrderDto::getId).toList();
                final List<ShopOrderItemDto> shopOrderItemDtoList = shopOrderItemService
                                .getAllByShopOrderIdList(shopOrderIdList);

                return shopOrderDtoList.stream().map(shopOrderDto -> {
                        final List<ShopOrderItemDto> filteredShopOrderItemDtoList = shopOrderItemDtoList.stream()
                                        .filter(shopOrderItemDto -> shopOrderItemDto.getShopOrderId()
                                                        .equals(shopOrderDto.getId()))
                                        .toList();
                        return ShopOrderMobileDto.builder()
                                        .shopOrderDto(shopOrderDto)
                                        .shopOrderItemDto(filteredShopOrderItemDtoList)
                                        .build();
                }).toList();
        }

        @Override
        public ShopOrderMobileDto getOrderByIdAndMemberId(Long id, Long memberId) {
                final ShopOrderDto shopOrderDto = shopOrderService.getByIdAndMemberId(id, memberId);
                final List<ShopOrderItemDto> shopOrderItemDtoList = shopOrderItemService
                                .getAllByShopOrderId(shopOrderDto.getId());

                return ShopOrderMobileDto.builder()
                                .shopOrderDto(shopOrderDto)
                                .shopOrderItemDto(shopOrderItemDtoList)
                                .build();
        }

        @Override
        public void validateOrder(ValidateOrderMobileRequestV1 validateOrderMobileRequestV1) {
                validateOrderMobileRequestV1.getItemList().forEach(item -> {
                        ShopProductVariantDto variant = shopProductVariantService
                                        .getById(item.getShopProductVariantId());
                        if (variant.getQuantity() < item.getQuantity()) {
                                throw new InsufficientStockException("재고가 부족하거나 주문을 처리할 수 없습니다.");
                        }
                });
        }
}
