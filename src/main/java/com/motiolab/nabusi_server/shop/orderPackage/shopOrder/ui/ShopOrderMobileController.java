package com.motiolab.nabusi_server.shop.orderPackage.shopOrder.ui;

import com.motiolab.nabusi_server.argumentResolver.MemberId;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.ShopOrderMobileService;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.ShopOrderMobileDto;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.CreateOrderMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.request.ValidateOrderMobileRequestV1;
import com.motiolab.nabusi_server.shop.orderPackage.shopOrder.application.dto.response.GetShopOrderListByMemberIdMobileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShopOrderMobileController {
    private final ShopOrderMobileService shopOrderMobileService;

    @PostMapping("/v1/mobile/shop/order")
    public ResponseEntity<ShopOrderMobileDto> createOrder(final @MemberId Long memberId,
            final @RequestBody CreateOrderMobileRequestV1 createOrderMobileRequestV1) {
        createOrderMobileRequestV1.setMemberId(memberId);
        final ShopOrderMobileDto shopOrderMobileDto = shopOrderMobileService.create(createOrderMobileRequestV1);
        return ResponseEntity.ok(shopOrderMobileDto);
    }

    @PostMapping("/v1/mobile/shop/order/validate")
    public ResponseEntity<Void> validateOrder(final @MemberId Long memberId,
            final @RequestBody ValidateOrderMobileRequestV1 validateOrderMobileRequestV1) {
        shopOrderMobileService.validateOrder(validateOrderMobileRequestV1);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/mobile/shop/order/list")
    public ResponseEntity<List<GetShopOrderListByMemberIdMobileResponse>> getOrderListByMemberId(final @MemberId Long memberId) {
        final List<ShopOrderMobileDto> shopOrderMobileDtoList = shopOrderMobileService.getOrderListByMemberId(memberId);

        final List<GetShopOrderListByMemberIdMobileResponse> getShopOrderListByMemberIdMobileResponseList = shopOrderMobileDtoList.stream()
                .map(it -> {
                    final GetShopOrderListByMemberIdMobileResponse.ShopOrderResponse shopOrderResponse = GetShopOrderListByMemberIdMobileResponse.ShopOrderResponse.builder()
                            .id(it.getShopOrderDto().getId())
                            .memberId(it.getShopOrderDto().getMemberId())
                            .paymentId(it.getShopOrderDto().getPaymentId())
                            .purchaseConfirmation(it.getShopOrderDto().getPurchaseConfirmation())
                            .status(it.getShopOrderDto().getStatus())
                            .totalPrice(it.getShopOrderDto().getTotalPrice())
                            .totalDiscountPrice(it.getShopOrderDto().getTotalDiscountPrice())
                            .totalAdditionalPrice(it.getShopOrderDto().getTotalAdditionalPrice())
                            .receiverName(it.getShopOrderDto().getReceiverName())
                            .receiverPhone(it.getShopOrderDto().getReceiverPhone())
                            .receiverAddress(it.getShopOrderDto().getReceiverAddress())
                            .receiverAddressCode(it.getShopOrderDto().getReceiverAddressCode())
                            .receiverDetailAddress(it.getShopOrderDto().getReceiverDetailAddress())
                            .createdDate(it.getShopOrderDto().getCreatedDate())
                            .lastUpdatedDate(it.getShopOrderDto().getLastUpdatedDate())
                            .build();
                    final List<GetShopOrderListByMemberIdMobileResponse.ShopOrderItemResponse> shopOrderItemResponseList = it.getShopOrderItemDto().stream()
                            .map(item -> GetShopOrderListByMemberIdMobileResponse.ShopOrderItemResponse.builder()
                                    .id(item.getId())
                                    .shopOrderId(item.getShopOrderId())
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
                                    .build()
                            ).toList();

                    return GetShopOrderListByMemberIdMobileResponse.builder()
                            .shopOrderResponse(shopOrderResponse)
                            .shopOrderItemResponseList(shopOrderItemResponseList)
                            .build();
                }
        ).toList();
        return ResponseEntity.ok(getShopOrderListByMemberIdMobileResponseList);
    }

    @GetMapping("/v1/mobile/shop/order/{id}")
    public ResponseEntity<ShopOrderMobileDto> getOrderByIdAndMemberId(final @PathVariable Long id,
            final @MemberId Long memberId) {
        final ShopOrderMobileDto shopOrderMobileDto = shopOrderMobileService.getOrderByIdAndMemberId(id, memberId);
        return ResponseEntity.ok(shopOrderMobileDto);
    }
}
