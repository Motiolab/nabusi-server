package com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.application;

import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.application.dto.TossPayEasyPayDto;
import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.domain.TossPayEasyPayEntity;
import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayEasyPay.domain.TossPayEasyPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossPayEasyPayServiceImpl implements TossPayEasyPayService {
    private final TossPayEasyPayRepository tossPayEasyPayRepository;

    @Override
    public TossPayEasyPayDto create(TossPayEasyPayDto tossPayEasyPayDto) {
        final TossPayEasyPayEntity tossPayEasyPayEntity = TossPayEasyPayEntity.create(
                tossPayEasyPayDto.getProvider(),
                tossPayEasyPayDto.getAmount(),
                tossPayEasyPayDto.getDiscountAmount()
        );
        final TossPayEasyPayEntity savedTossPayEasyPayEntity = tossPayEasyPayRepository.save(tossPayEasyPayEntity);
        return TossPayEasyPayDto.from(savedTossPayEasyPayEntity);
    }
}
