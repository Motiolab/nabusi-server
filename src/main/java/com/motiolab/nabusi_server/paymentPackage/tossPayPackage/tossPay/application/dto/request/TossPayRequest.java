package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.dto.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPayRequest {
    @JsonProperty("mId")
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private Integer taxExemptionAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private Boolean useEscrow;
    private Boolean cultureExpense;
    private Card card;
    private VirtualAccount virtualAccount;
    private Transfer transfer;
    private MobilePhone mobilePhone;
    private GiftCertificate giftCertificate;
    private CashReceipt cashReceipt;
    private Discount discount;
    private List<Cancels> cancels;
    private String secret;
    private String type;
    private EasyPay easyPay;
    private String country;
    private Failure failure;
    private Boolean isPartialCancelable;
    private Receipt receipt;
    private Checkout checkout;
    private String currency;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private Integer vat;
    private Integer taxFreeAmount;
    private String metadata;
    private String method;
    private String version;

    @Getter
    @Setter
    public static class Card {
        private String issuerCode;
        private String acquirerCode;
        private String number;
        private Integer installmentPlanMonths;
        private Boolean isInterestFree;
        private String interestPayer;
        private String approveNo;
        private Boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
        private String receiptUrl;
        private Integer amount;
    }

    @Getter
    @Setter
    public static class EasyPay {
        private String provider;
        private Integer amount;
        private Integer discountAmount;
    }

    @Getter
    @Setter
    public static class Receipt {
        private String url;
    }

    @Getter
    @Setter
    public static class Checkout {
        private String url;
    }

    @Getter
    @Setter
    public static class Failure {
        private String code;
        private String message;
    }

    @Getter
    @Setter
    public static class VirtualAccount {}

    @Getter
    @Setter
    public static class Transfer {}

    @Getter
    @Setter
    public static class MobilePhone {}

    @Getter
    @Setter
    public static class GiftCertificate {}

    @Getter
    @Setter
    public static class CashReceipt {}

    @Getter
    @Setter
    public static class Discount {}

    @Getter
    @Setter
    public static class Cancels {
        private String transactionKey;
        private String cancelReason;
        private Integer taxExemptionAmount;
        private String canceledAt;
        private Integer transferDiscountAmount;
        private Integer easyPayDiscountAmount;
        private String receiptKey;
        private Integer cancelAmount;
        private Integer taxFreeAmount;
        private Integer refundableAmount;
        private String cancelStatus;
        private String cancelRequestId;
    }
}

