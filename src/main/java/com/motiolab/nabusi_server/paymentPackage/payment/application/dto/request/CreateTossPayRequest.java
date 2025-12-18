package com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateTossPayRequest {
    private String paymentKey;
    private String amount;
    private String orderId;
    private Long memberId;
    private Long wellnessLectureId;
}
