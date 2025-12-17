package com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.application.dto;

import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.domain.TossPayFailureEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TossPayFailureDto {
    private Long id;
    private String code;
    private String message;

    public static TossPayFailureDto from(TossPayFailureEntity tossPayFailureEntity) {
        return TossPayFailureDto.builder()
                .id(tossPayFailureEntity.getId())
                .code(tossPayFailureEntity.getCode())
                .message(tossPayFailureEntity.getMessage())
                .build();
    }
}
