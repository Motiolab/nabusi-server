package com.motiolab.nabusi_server.paymentPackage.payment.application;

import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;
import com.motiolab.nabusi_server.paymentPackage.payment.domain.PaymentEntity;
import com.motiolab.nabusi_server.paymentPackage.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDto create(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = PaymentEntity.create(
                paymentDto.getMemberId(),
                paymentDto.getTossPayId(),
                paymentDto.getOnSitePayId(),
                paymentDto.getStatus()
        );
        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        return PaymentDto.from(savedPaymentEntity);
    }

    @Override
    public PaymentDto update(PaymentDto paymentDto) {
        final PaymentEntity paymentEntity  = paymentRepository.findById(paymentDto.getId())
                .orElseThrow(() -> new NotFoundException(PaymentEntity.class, paymentDto.getId()));

        paymentEntity.update(
                paymentDto.getMemberId(),
                paymentDto.getTossPayId(),
                paymentDto.getOnSitePayId(),
                paymentDto.getStatus()
        );

        PaymentEntity savedPaymentEntity = paymentRepository.save(paymentEntity);
        return PaymentDto.from(savedPaymentEntity);
    }

    @Override
    public PaymentDto getByTossPayId(Long tossPayId) {
        final PaymentEntity paymentEntity = paymentRepository.findByTossPayId(tossPayId)
                .orElseThrow(() -> new NotFoundException(PaymentEntity.class, tossPayId));

        return PaymentDto.from(paymentEntity);
    }
}
