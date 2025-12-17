package com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.application;

import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.application.dto.TossPayCancelDto;

import java.util.List;

public interface TossPayCancelService {
    void create(TossPayCancelDto tossPayCancelDto);
    void createAll(List<TossPayCancelDto> tossPayCancelDtoList);
}
