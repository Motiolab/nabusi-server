package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application;

import com.motiolab.nabusi_server.memberPackage.memberPoint.application.MemberPointService;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.MemberPointHistoryService;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.MemberPointHistoryDto;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.domain.PointTransactionType;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderMobileDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CreateOrderWithPaymentConfirmMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.ValidateOrderMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application.ShopOrderItemService;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application.dto.ShopOrderItemDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import com.motiolab.nabusi_server.exception.customException.InsufficientStockException;
import com.motiolab.nabusi_server.exception.customException.PaymentFailureException;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.ShopProductVariantService;
import com.motiolab.nabusi_server.shop.productPackage.shopProductVariant.application.dto.ShopProductVariantDto;
import com.motiolab.nabusi_server.shop.productPackage.shopProduct.application.ShopProductService;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.application.ShopCartService;
import com.motiolab.nabusi_server.shop.cartPackage.shopCart.domain.CartStatus;
import com.motiolab.nabusi_server.paymentPackage.payment.application.PaymentMobileService;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CreateTossPayRequest;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;
import com.motiolab.nabusi_server.fcmTokenMobile.application.FcmTokenMobileService;
import com.motiolab.nabusi_server.notificationPackage.notificationFcm.application.NotificationFcmAdminService;

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
        private final PaymentMobileService paymentMobileService;
        private final FcmTokenMobileService fcmTokenMobileService;
        private final NotificationFcmAdminService notificationFcmAdminService;

        @Transactional
        @Override
        public ShopOrderMobileDto createOrderWithPaymentConfirm(
                        CreateOrderWithPaymentConfirmMobileRequestV1 createOrderWithPaymentConfirmMobileRequestV1) {
                this.validateOrder(ValidateOrderMobileRequestV1.builder()
                                .itemList(createOrderWithPaymentConfirmMobileRequestV1.getItemList().stream()
                                                .map(item -> ValidateOrderMobileRequestV1.Item.builder()
                                                                .shopProductVariantId(item.getShopProductVariantId())
                                                                .quantity(item.getQuantity())
                                                                .build())
                                                .toList())
                                .build());

                final List<ShopProductVariantDto> shopProductVariantDtoList = shopProductVariantService.getAllByIdList(
                                createOrderWithPaymentConfirmMobileRequestV1.getItemList().stream()
                                                .map(CreateOrderWithPaymentConfirmMobileRequestV1.Item::getShopProductVariantId)
                                                .toList());

                final List<ShopProductVariantDto> updatedShopProductVariantDtoList = shopProductVariantDtoList.stream()
                                .map(it -> shopProductVariantService.update(ShopProductVariantDto.builder()
                                                .id(it.getId())
                                                .shopProductId(it.getShopProductId())
                                                .optionName(it.getOptionName())
                                                .additionalPrice(it.getAdditionalPrice())
                                                .display(it.getDisplay())
                                                .selling(it.getSelling())
                                                .displaySoldOut(it.getDisplaySoldOut())
                                                .quantity(it.getQuantity()
                                                                - createOrderWithPaymentConfirmMobileRequestV1
                                                                                .getItemList()
                                                                                .stream()
                                                                                .filter(item -> item
                                                                                                .getShopProductVariantId()
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

                final PaymentDto paymentDto = paymentMobileService.createReservationWithTossPay(CreateTossPayRequest
                                .builder()
                                .paymentKey(createOrderWithPaymentConfirmMobileRequestV1.getPaymentKey())
                                .amount(createOrderWithPaymentConfirmMobileRequestV1.getAmount())
                                .orderId(createOrderWithPaymentConfirmMobileRequestV1.getOrderId())
                                .memberId(createOrderWithPaymentConfirmMobileRequestV1.getMemberId())
                                .build());

                if (paymentDto == null) {
                        throw new PaymentFailureException("결제 승인 요청에 실패했습니다.");
                }

                final ShopOrderDto shopOrderDto = ShopOrderDto.builder()
                                .memberId(createOrderWithPaymentConfirmMobileRequestV1.getMemberId())
                                .paymentId(paymentDto.getId())
                                .purchaseConfirmation(
                                                createOrderWithPaymentConfirmMobileRequestV1.getPurchaseConfirmation())
                                .status(createOrderWithPaymentConfirmMobileRequestV1.getStatus())
                                .totalPrice(createOrderWithPaymentConfirmMobileRequestV1.getTotalPrice())
                                .totalDiscountPrice(
                                                createOrderWithPaymentConfirmMobileRequestV1.getTotalDiscountPrice())
                                .totalAdditionalPrice(
                                                createOrderWithPaymentConfirmMobileRequestV1.getTotalAdditionalPrice())
                                .receiverName(createOrderWithPaymentConfirmMobileRequestV1.getReceiverName())
                                .receiverPhone(createOrderWithPaymentConfirmMobileRequestV1.getReceiverPhone())
                                .receiverAddress(createOrderWithPaymentConfirmMobileRequestV1.getReceiverAddress())
                                .receiverAddressCode(
                                                createOrderWithPaymentConfirmMobileRequestV1.getReceiverAddressCode())
                                .receiverDetailAddress(
                                                createOrderWithPaymentConfirmMobileRequestV1.getReceiverDetailAddress())
                                .build();

                final ShopOrderDto storedShopOrderDto = shopOrderService.save(shopOrderDto);
                final List<ShopOrderItemDto> shopOrderItemDtoList = createOrderWithPaymentConfirmMobileRequestV1
                                .getItemList()
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

                if (createOrderWithPaymentConfirmMobileRequestV1.getShopCartId() != null) {
                        shopCartService.updateStatus(createOrderWithPaymentConfirmMobileRequestV1.getShopCartId(),
                                        CartStatus.COMPLETE_ORDER);
                }

                if (createOrderWithPaymentConfirmMobileRequestV1.getUsedPoint() > 0) {
                        memberPointService.addPoint(createOrderWithPaymentConfirmMobileRequestV1.getMemberId(),
                                        createOrderWithPaymentConfirmMobileRequestV1.getUsedPoint());
                        memberPointHistoryService.create(MemberPointHistoryDto.builder()
                                        .memberId(createOrderWithPaymentConfirmMobileRequestV1.getMemberId())
                                        .amount(createOrderWithPaymentConfirmMobileRequestV1.getUsedPoint())
                                        .transactionType(PointTransactionType.USE)
                                        .description("주문 시 포인트 사용")
                                        .referenceId("paymentID")
                                        .build());
                } else if (createOrderWithPaymentConfirmMobileRequestV1.getRewardPoint() > 0) {
                        memberPointService.addPoint(createOrderWithPaymentConfirmMobileRequestV1.getMemberId(),
                                        createOrderWithPaymentConfirmMobileRequestV1.getRewardPoint());
                        memberPointHistoryService.create(MemberPointHistoryDto.builder()
                                        .memberId(createOrderWithPaymentConfirmMobileRequestV1.getMemberId())
                                        .amount(createOrderWithPaymentConfirmMobileRequestV1.getRewardPoint())
                                        .transactionType(PointTransactionType.EARN)
                                        .description("주문 시 포인트 적립")
                                        .referenceId("paymentID")
                                        .build());
                }

                final String fcmToken = fcmTokenMobileService
                                .getFcmTokenByMemberId(createOrderWithPaymentConfirmMobileRequestV1.getMemberId());
                if (fcmToken != null) {
                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, "주문 완료", "주문이 완료되었습니다.");
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
