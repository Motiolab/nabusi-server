package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application;

import com.motiolab.nabusi_server.memberPackage.memberPoint.application.MemberPointService;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.MemberPointHistoryService;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.application.dto.MemberPointHistoryDto;
import com.motiolab.nabusi_server.memberPackage.memberPointHistory.domain.PointTransactionType;
import com.motiolab.nabusi_server.memberPackage.member.application.MemberService;
import com.motiolab.nabusi_server.memberPackage.member.application.dto.MemberDto;
import com.motiolab.nabusi_server.notificationPackage.notificationFcmHistory.application.FcmNotificationHistoryService;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderMobileDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CreateOrderWithPaymentConfirmMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.ValidateOrderMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application.ShopOrderItemService;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrderItem.application.dto.ShopOrderItemDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.domain.ShopOrderRepository;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CancelShopOrderMobileRequestV1;
import com.motiolab.nabusi_server.paymentPackage.payment.application.PaymentService;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.TossPayService;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.domain.ShopOrderEntity;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.enums.ShopOrderStatus;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CancelTossPayRequest;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.dto.TossPayDto;

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
        private final ShopOrderRepository shopOrderRepository;
        private final PaymentService paymentService;
        private final TossPayService tossPayService;
        private final MemberService memberService;
        private final FcmNotificationHistoryService fcmNotificationHistoryService;

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
                                .usedPoint(createOrderWithPaymentConfirmMobileRequestV1.getUsedPoint())
                                .rewardPoint(createOrderWithPaymentConfirmMobileRequestV1.getRewardPoint())
                                .shopCartId(createOrderWithPaymentConfirmMobileRequestV1.getShopCartId())
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
                        final MemberDto author = memberService
                                        .getById(createOrderWithPaymentConfirmMobileRequestV1.getMemberId());
                        final String title = "주문 완료";
                        final String body = "주문이 완료되었습니다.";
                        final String productDetail = storedShopOrderItemDtoList.stream()
                                        .map(item -> "상품명: " + item.getProductName() + " (" + item.getOptionName()
                                                        + ") / 수량: " + item.getQuantity())
                                        .collect(Collectors.joining("\n"));

                        final String detail = "[주문 완료 정보]\n" +
                                        "주문자: " + author.getName() + " (" + author.getMobile() + ")\n" +
                                        "주문일시: " + LocalDateTime.now()
                                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                        + "\n" +
                                        "주문 ID: " + storedShopOrderDto.getId() + "\n" +
                                        "결제 금액: " + storedShopOrderDto.getTotalPrice() + "\n" +
                                        "\n" +
                                        "[상품 정보]\n" +
                                        productDetail;
                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, title, body);
                        fcmNotificationHistoryService.save(createOrderWithPaymentConfirmMobileRequestV1.getMemberId(),
                                        title,
                                        body, detail);
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

        @Transactional
        @Override
        public void cancelOrder(CancelShopOrderMobileRequestV1 cancelShopOrderMobileRequestV1) {
                ShopOrderEntity shopOrderEntity = shopOrderRepository
                                .findById(cancelShopOrderMobileRequestV1.getShopOrderId())
                                .orElseThrow(() -> new RuntimeException("주문 정보를 찾을 수 없습니다."));

                final PaymentDto paymentDto = paymentService.getById(shopOrderEntity.getPaymentId());
                final TossPayDto tossPayDto = tossPayService.getById(paymentDto.getTossPayId());

                // 1. Toss Payments 취소
                paymentMobileService.cancelTossPay(CancelTossPayRequest.builder()
                                .paymentKey(tossPayDto.getPaymentKey())
                                .cancelReason(cancelShopOrderMobileRequestV1.getCancelReason())
                                .build());

                // 2. 재고 복구
                final List<ShopOrderItemDto> orderItems = shopOrderItemService
                                .getAllByShopOrderId(shopOrderEntity.getId());
                orderItems.forEach(item -> {
                        ShopProductVariantDto variant = shopProductVariantService
                                        .getById(item.getShopProductVariantId());
                        shopProductVariantService.update(ShopProductVariantDto.builder()
                                        .id(variant.getId())
                                        .shopProductId(variant.getShopProductId())
                                        .optionName(variant.getOptionName())
                                        .additionalPrice(variant.getAdditionalPrice())
                                        .display(variant.getDisplay())
                                        .selling(variant.getSelling())
                                        .displaySoldOut(variant.getDisplaySoldOut())
                                        .quantity(variant.getQuantity() + item.getQuantity())
                                        .build());

                        // 품절 상태 해제 체크
                        shopProductService.updateSoldOut(variant.getShopProductId(), "false");
                });

                // 3. 포인트 복구
                if (shopOrderEntity.getUsedPoint() != null && shopOrderEntity.getUsedPoint() > 0) {
                        memberPointService.addPoint(shopOrderEntity.getMemberId(), shopOrderEntity.getUsedPoint());
                        memberPointHistoryService.create(MemberPointHistoryDto.builder()
                                        .memberId(shopOrderEntity.getMemberId())
                                        .amount(shopOrderEntity.getUsedPoint())
                                        .transactionType(PointTransactionType.CANCEL)
                                        .description("주문 취소로 인한 포인트 복구")
                                        .referenceId(paymentDto.getId().toString())
                                        .build());
                }

                // 적립된 포인트 회수
                if (shopOrderEntity.getRewardPoint() != null && shopOrderEntity.getRewardPoint() > 0) {
                        memberPointService.addPoint(shopOrderEntity.getMemberId(), -shopOrderEntity.getRewardPoint());
                        memberPointHistoryService.create(MemberPointHistoryDto.builder()
                                        .memberId(shopOrderEntity.getMemberId())
                                        .amount(shopOrderEntity.getRewardPoint())
                                        .transactionType(PointTransactionType.CANCEL)
                                        .description("주문 취소로 인한 적립 포인트 회수")
                                        .referenceId(paymentDto.getId().toString())
                                        .build());
                }

                // 4. FCM 알림 발송
                final String fcmToken = fcmTokenMobileService.getFcmTokenByMemberId(shopOrderEntity.getMemberId());
                if (fcmToken != null) {
                        final MemberDto author = memberService.getById(shopOrderEntity.getMemberId());
                        final String title = "주문 취소 완료";
                        final String body = "주문이 취소되었습니다.";
                        final String productDetail = orderItems.stream()
                                        .map(item -> "상품명: " + item.getProductName() + " (" + item.getOptionName()
                                                        + ") / 수량: " + item.getQuantity())
                                        .collect(Collectors.joining("\n"));

                        final String detail = "[주문 취소 완료 정보]\n" +
                                        "취소 요청자: " + author.getName() + " (" + author.getMobile() + ")\n" +
                                        "취소일시: " + LocalDateTime.now()
                                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                        + "\n" +
                                        "취소 주문 ID: " + shopOrderEntity.getId() + "\n" +
                                        "취소 사유: " + cancelShopOrderMobileRequestV1.getCancelReason() + "\n" +
                                        "\n" +
                                        "[상품 정보]\n" +
                                        productDetail;

                        notificationFcmAdminService.sendNotificationFcmTest(fcmToken, title, body);
                        fcmNotificationHistoryService.save(shopOrderEntity.getMemberId(), title, body, detail);
                }
                // 5. 주문 상태 변경
                shopOrderEntity.updateStatus(ShopOrderStatus.CANCELED);
                shopOrderRepository.save(shopOrderEntity);
        }
}
