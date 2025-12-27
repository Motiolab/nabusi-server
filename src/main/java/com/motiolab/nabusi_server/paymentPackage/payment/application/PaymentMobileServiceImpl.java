package com.motiolab.nabusi_server.paymentPackage.payment.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.WellnessLectureService;
import com.motiolab.nabusi_server.classPackage.wellnessLecture.application.dto.WellnessLectureDto;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CancelTossPayRequest;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CreateTossPayRequest;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.TossPayFailureRequest;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.response.TossPayResponse;
import com.motiolab.nabusi_server.paymentPackage.payment.enums.PaymentStatusEnum;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.TossPayServiceImpl;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.dto.TossPayDto;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.application.TossPayCancelService;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.application.dto.TossPayCancelDto;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.application.TossPayCardService;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.application.dto.TossPayCardDto;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.application.TossPayEasyPayService;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.application.dto.TossPayEasyPayDto;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.application.TossPayFailureService;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.application.dto.TossPayFailureDto;
import com.motiolab.nabusi_server.reservation.application.ReservationService;
import com.motiolab.nabusi_server.reservation.application.dto.ReservationDto;
import com.motiolab.nabusi_server.reservation.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentMobileServiceImpl implements PaymentMobileService {
    @Value("${toss-pay.secretKey}")
    private String secretKey;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();
    private final TossPayServiceImpl tossPayService;
    private final TossPayCardService tossPayCardService;
    private final TossPayEasyPayService tossPayEasyPayService;
    private final TossPayFailureService tossPayFailureService;
    private final TossPayCancelService tossPayCancelService;
    private final PaymentService paymentService;
    private final WellnessLectureService wellnessLectureService;
    private final ReservationService reservationService;

    @Override
    public PaymentDto createReservationWithTossPay(CreateTossPayRequest createTossPayRequest) {
        String url = "https://api.tosspayments.com/v1/payments/confirm";

        final String authKey = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + authKey);
        headers.set("Content-Type", "application/json");

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(java.util.Map.of(
                    "paymentKey", createTossPayRequest.getPaymentKey(),
                    "amount", createTossPayRequest.getAmount(),
                    "orderId", createTossPayRequest.getOrderId()));
        } catch (Exception e) {
            throw new RuntimeException("Error creating request body", e);
        }

        final HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        final ResponseEntity<TossPayResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                TossPayResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                TossPayResponse tossPayResponse = response.getBody();
                assert tossPayResponse != null;
                TossPayCardDto savedTossPayCardDto = null;
                if (tossPayResponse.getCard() != null) {
                    final TossPayCardDto tossPayCardDto = TossPayCardDto.builder()
                            .issuerCode(tossPayResponse.getCard().getIssuerCode())
                            .acquirerCode(tossPayResponse.getCard().getAcquirerCode())
                            .number(tossPayResponse.getCard().getNumber())
                            .installmentPlanMonths(tossPayResponse.getCard().getInstallmentPlanMonths())
                            .isInterestFree(tossPayResponse.getCard().getIsInterestFree())
                            .interestPayer(tossPayResponse.getCard().getInterestPayer())
                            .approveNo(tossPayResponse.getCard().getApproveNo())
                            .useCardPoint(tossPayResponse.getCard().getUseCardPoint())
                            .cardType(tossPayResponse.getCard().getCardType())
                            .ownerType(tossPayResponse.getCard().getOwnerType())
                            .acquireStatus(tossPayResponse.getCard().getAcquireStatus())
                            .receiptUrl(tossPayResponse.getCard().getReceiptUrl())
                            .amount(tossPayResponse.getCard().getAmount())
                            .build();
                    savedTossPayCardDto = tossPayCardService.create(tossPayCardDto);
                }

                TossPayEasyPayDto savedTossPayEasyPayDto = null;
                if (tossPayResponse.getEasyPay() != null) {
                    final TossPayEasyPayDto tossPayEasyPayDto = TossPayEasyPayDto.builder()
                            .provider(tossPayResponse.getEasyPay().getProvider())
                            .amount(tossPayResponse.getEasyPay().getAmount())
                            .discountAmount(tossPayResponse.getEasyPay().getDiscountAmount())
                            .build();
                    savedTossPayEasyPayDto = tossPayEasyPayService.create(tossPayEasyPayDto);
                }

                final TossPayDto tossPayDto = TossPayDto.builder()
                        .memberId(createTossPayRequest.getMemberId())
                        .wellnessLectureId(0L)
                        .mId(tossPayResponse.getMId())
                        .lastTransactionKey(tossPayResponse.getLastTransactionKey())
                        .paymentKey(tossPayResponse.getPaymentKey())
                        .orderId(tossPayResponse.getOrderId())
                        .orderName(tossPayResponse.getOrderName())
                        .taxExemptionAmount(tossPayResponse.getTaxExemptionAmount())
                        .status(tossPayResponse.getStatus())
                        .requestedAt(tossPayResponse.getRequestedAt())
                        .approvedAt(tossPayResponse.getApprovedAt())
                        .useEscrow(tossPayResponse.getUseEscrow())
                        .cultureExpense(tossPayResponse.getCultureExpense())
                        .tossPayCardId(savedTossPayCardDto == null ? null : savedTossPayCardDto.getId())
                        .type(tossPayResponse.getType())
                        .tossPayEasyPayId(savedTossPayEasyPayDto == null ? null : savedTossPayEasyPayDto.getId())
                        .country(tossPayResponse.getCountry())
                        .isPartialCancelable(tossPayResponse.getIsPartialCancelable())
                        .receiptUrl(tossPayResponse.getReceipt().getUrl())
                        .checkoutUrl(tossPayResponse.getCheckout().getUrl())
                        .currency(tossPayResponse.getCurrency())
                        .totalAmount(tossPayResponse.getTotalAmount())
                        .balanceAmount(tossPayResponse.getBalanceAmount())
                        .suppliedAmount(tossPayResponse.getSuppliedAmount())
                        .vat(tossPayResponse.getVat())
                        .taxFreeAmount(tossPayResponse.getTaxFreeAmount())
                        .method(tossPayResponse.getMethod())
                        .version(tossPayResponse.getVersion())
                        .build();

                final TossPayDto savedTossPayDto = tossPayService.create(tossPayDto);
                final PaymentDto paymentDto = PaymentDto.builder()
                        .memberId(createTossPayRequest.getMemberId())
                        .tossPayId(savedTossPayDto.getId())
                        .status(PaymentStatusEnum.PAID)
                        .build();
                return paymentService.create(paymentDto);
            } catch (Exception e) {
                e.printStackTrace(); // JSON 파싱 예외 처리
            }
        } else {
            System.out.println("Payment confirmation failed. Status: " + response.getStatusCode());
            ResponseEntity<TossPayFailureRequest> tossPayFailureRes = restTemplate.exchange(url, HttpMethod.POST,
                    entity, TossPayFailureRequest.class);
            TossPayFailureRequest tossPayFailureRequest = tossPayFailureRes.getBody();
            if (tossPayFailureRequest != null) {
                final TossPayFailureDto tossPayFailureDto = TossPayFailureDto.builder()
                        .code(tossPayFailureRequest.getCode())
                        .message(tossPayFailureRequest.getMessage())
                        .build();
                tossPayFailureService.create(tossPayFailureDto);
                System.out.println(tossPayFailureRequest.getMessage());
            }
        }
        return null;
    }

    @Override
    public void cancelTossPay(CancelTossPayRequest cancelTossPayRequest) {
        if (cancelTossPayRequest.getPaymentKey() == null) {
            throw new RuntimeException("PaymentKey is null");
        }

        final TossPayDto tossPayDto = tossPayService.getByPaymentKey(cancelTossPayRequest.getPaymentKey());
        if (tossPayDto == null) {
            throw new RuntimeException("TossPayEntity is null, paymentKey : " + cancelTossPayRequest.getPaymentKey());
        }

        String url = "https://api.tosspayments.com/v1/payments/" + cancelTossPayRequest.getPaymentKey() + "/cancel";

        final String authKey = Base64.getEncoder().encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + authKey);
        headers.set("Content-Type", "application/json");

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(java.util.Map.of(
                    "cancelReason", cancelTossPayRequest.getCancelReason()));
        } catch (Exception e) {
            throw new RuntimeException("Error creating request body", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<TossPayResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                TossPayResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            final TossPayResponse tossPayResponse = response.getBody();

            // push 알림 보내기
            // kakao 알림 보내기

            // tospay 테이블에 업데이트

            PaymentDto paymentDto = paymentService.getByTossPayId(tossPayDto.getId());
            paymentDto.setStatus(PaymentStatusEnum.CANCELE);
            paymentService.update(paymentDto);

            tossPayDto.setLastTransactionKey(Objects.requireNonNull(tossPayResponse).getLastTransactionKey());
            tossPayDto.setStatus(tossPayResponse.getStatus());
            tossPayDto.setRequestedAt(tossPayResponse.getRequestedAt());
            tossPayDto.setApprovedAt(tossPayResponse.getApprovedAt());
            tossPayService.update(tossPayDto);

            final List<TossPayCancelDto> tossPayCancelDtoList = tossPayResponse.getCancels()
                    .stream()
                    .map(cancels -> TossPayCancelDto.builder()
                            .tossPayId(tossPayDto.getId())
                            .transactionKey(cancels.getTransactionKey())
                            .cancelReason(cancels.getCancelReason())
                            .taxExemptionAmount(cancels.getTaxExemptionAmount())
                            .canceledAt(cancels.getCanceledAt())
                            .transferDiscountAmount(cancels.getTransferDiscountAmount())
                            .easyPayDiscountAmount(cancels.getEasyPayDiscountAmount())
                            .receiptKey(cancels.getReceiptKey())
                            .cancelAmount(cancels.getCancelAmount())
                            .taxFreeAmount(cancels.getTaxFreeAmount())
                            .refundableAmount(cancels.getRefundableAmount())
                            .cancelStatus(cancels.getCancelStatus())
                            .cancelRequestId(cancels.getCancelRequestId())
                            .build())
                    .toList();
            tossPayCancelService.createAll(tossPayCancelDtoList);
        } else {
            System.out.println("Payment confirmation failed. Status: " + response.getStatusCode());
            ResponseEntity<TossPayFailureRequest> tossPayFailureRes = restTemplate.exchange(url, HttpMethod.POST,
                    entity, TossPayFailureRequest.class);
            TossPayFailureRequest tossPayFailureRequest = tossPayFailureRes.getBody();
            if (tossPayFailureRequest != null) {
                final TossPayFailureDto tossPayFailureDto = TossPayFailureDto.builder()
                        .code(tossPayFailureRequest.getCode())
                        .message(tossPayFailureRequest.getMessage())
                        .build();
                final TossPayFailureDto savedTossPayFailureDto = tossPayFailureService.create(tossPayFailureDto);

                tossPayDto.setTossPayFailureId(savedTossPayFailureDto.getId());
                tossPayService.update(tossPayDto);
            }
            System.out.println(tossPayFailureRequest.getMessage());
        }
    }
}
