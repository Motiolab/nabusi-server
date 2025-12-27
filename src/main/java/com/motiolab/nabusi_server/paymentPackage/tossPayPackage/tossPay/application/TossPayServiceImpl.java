package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application;

import com.motiolab.nabusi_server.classPackage.wellnessLecture.domain.WellnessLectureEntity;
import com.motiolab.nabusi_server.exception.customException.NotFoundException;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.application.dto.TossPayDto;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.domain.TossPayEntity;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.domain.TossPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossPayServiceImpl implements TossPayService {
    private final TossPayRepository tossPayRepository;

    @Override
    public TossPayDto create(TossPayDto tossPayDto) {
        final TossPayEntity tossPayEntity = TossPayEntity.create(
                tossPayDto.getMemberId(),
                tossPayDto.getWellnessLectureId(),
                tossPayDto.getMId(),
                tossPayDto.getLastTransactionKey(),
                tossPayDto.getPaymentKey(),
                tossPayDto.getOrderId(),
                tossPayDto.getOrderName(),
                tossPayDto.getTaxExemptionAmount(),
                tossPayDto.getStatus(),
                tossPayDto.getRequestedAt(),
                tossPayDto.getApprovedAt(),
                tossPayDto.getUseEscrow(),
                tossPayDto.getCultureExpense(),
                tossPayDto.getTossPayCardId(),
                tossPayDto.getType(),
                tossPayDto.getTossPayEasyPayId(),
                tossPayDto.getCountry(),
                tossPayDto.getTossPayFailureId(),
                tossPayDto.getIsPartialCancelable(),
                tossPayDto.getReceiptUrl(),
                tossPayDto.getCheckoutUrl(),
                tossPayDto.getCurrency(),
                tossPayDto.getTotalAmount(),
                tossPayDto.getBalanceAmount(),
                tossPayDto.getSuppliedAmount(),
                tossPayDto.getVat(),
                tossPayDto.getTaxFreeAmount(),
                tossPayDto.getMethod(),
                tossPayDto.getVersion());
        TossPayEntity savedTossPayEntity = tossPayRepository.save(tossPayEntity);
        return TossPayDto.from(savedTossPayEntity);
    }

    @Override
    public TossPayDto getByPaymentKey(String paymentKey) {
        return tossPayRepository.findByPaymentKey(paymentKey)
                .map(TossPayDto::from)
                .orElse(null);
    }

    @Override
    public void update(TossPayDto tossPayDto) {
        tossPayRepository.findById(tossPayDto.getId())
                .map(tossPayEntity -> {
                    tossPayEntity.update(
                            tossPayDto.getMemberId(),
                            tossPayDto.getWellnessLectureId(),
                            tossPayDto.getMId(),
                            tossPayDto.getLastTransactionKey(),
                            tossPayDto.getPaymentKey(),
                            tossPayDto.getOrderId(),
                            tossPayDto.getOrderName(),
                            tossPayDto.getTaxExemptionAmount(),
                            tossPayDto.getStatus(),
                            tossPayDto.getRequestedAt(),
                            tossPayDto.getApprovedAt(),
                            tossPayDto.getUseEscrow(),
                            tossPayDto.getCultureExpense(),
                            tossPayDto.getTossPayCardId(),
                            tossPayDto.getType(),
                            tossPayDto.getTossPayEasyPayId(),
                            tossPayDto.getCountry(),
                            tossPayDto.getTossPayFailureId(),
                            tossPayDto.getIsPartialCancelable(),
                            tossPayDto.getReceiptUrl(),
                            tossPayDto.getCheckoutUrl(),
                            tossPayDto.getCurrency(),
                            tossPayDto.getTotalAmount(),
                            tossPayDto.getBalanceAmount(),
                            tossPayDto.getSuppliedAmount(),
                            tossPayDto.getVat(),
                            tossPayDto.getTaxFreeAmount(),
                            tossPayDto.getMethod(),
                            tossPayDto.getVersion());
                    return tossPayRepository.save(tossPayEntity);
                })
                .orElseThrow(() -> new NotFoundException(WellnessLectureEntity.class, tossPayDto.getId()));
    }

    @Override
    public TossPayDto getById(Long id) {
        return tossPayRepository.findById(id)
                .map(TossPayDto::from)
                .orElseThrow(() -> new NotFoundException(TossPayEntity.class, id));
    }
}
