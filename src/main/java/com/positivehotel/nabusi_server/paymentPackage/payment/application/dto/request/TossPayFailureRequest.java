package com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPayFailureRequest {
    private String code;
    private String message;
}

