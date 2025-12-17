package com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.application;

import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.application.dto.TossPayCancelDto;
import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.domain.TossPayCancelEntity;
import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCancel.domain.TossPayCancelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TossPayCancelServiceImpl implements TossPayCancelService{
    private final TossPayCancelRepository tossPayCancelRepository;

    @Override
    public void create(TossPayCancelDto tossPayCancelDto) {
        TossPayCancelEntity tossPayCancelEntity = TossPayCancelEntity.create(
                tossPayCancelDto.getTossPayId(),
                tossPayCancelDto.getTransactionKey(),
                tossPayCancelDto.getCancelReason(),
                tossPayCancelDto.getTaxExemptionAmount(),
                tossPayCancelDto.getCanceledAt(),
                tossPayCancelDto.getTransferDiscountAmount(),
                tossPayCancelDto.getEasyPayDiscountAmount(),
                tossPayCancelDto.getReceiptKey(),
                tossPayCancelDto.getCancelAmount(),
                tossPayCancelDto.getTaxFreeAmount(),
                tossPayCancelDto.getRefundableAmount(),
                tossPayCancelDto.getCancelStatus(),
                tossPayCancelDto.getCancelRequestId()
        );
        tossPayCancelRepository.save(tossPayCancelEntity);
    }

    @Override
    public void createAll(List<TossPayCancelDto> tossPayCancelDtoList) {
        List<TossPayCancelEntity> tossPayCancelEntityList = tossPayCancelDtoList.stream()
                .map(tossPayCancelDto ->  TossPayCancelEntity.create(
                        tossPayCancelDto.getTossPayId(),
                        tossPayCancelDto.getTransactionKey(),
                        tossPayCancelDto.getCancelReason(),
                        tossPayCancelDto.getTaxExemptionAmount(),
                        tossPayCancelDto.getCanceledAt(),
                        tossPayCancelDto.getTransferDiscountAmount(),
                        tossPayCancelDto.getEasyPayDiscountAmount(),
                        tossPayCancelDto.getReceiptKey(),
                        tossPayCancelDto.getCancelAmount(),
                        tossPayCancelDto.getTaxFreeAmount(),
                        tossPayCancelDto.getRefundableAmount(),
                        tossPayCancelDto.getCancelStatus(),
                        tossPayCancelDto.getCancelRequestId()
                )).toList();

        tossPayCancelRepository.saveAll(tossPayCancelEntityList);
    }
}
