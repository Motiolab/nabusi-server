package com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CancelTossPayRequest {
    private String paymentKey;
    private String cancelReason;
    private Long reservationId;
}
