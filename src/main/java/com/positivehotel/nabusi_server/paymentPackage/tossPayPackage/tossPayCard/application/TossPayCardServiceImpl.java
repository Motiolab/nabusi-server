package com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.application;

import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.application.dto.TossPayCardDto;
import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.domain.TossPayCardEntity;
import com.positivehotel.nabusi_server.paymentPackage.tossPayPackage.tossPayCard.domain.TossPayCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossPayCardServiceImpl implements TossPayCardService {
    private final TossPayCardRepository tossPayCardRepository;

    @Override
    public TossPayCardDto create(TossPayCardDto tossPayCardDto) {
        final TossPayCardEntity tossPayCardEntity = TossPayCardEntity.create(
                tossPayCardDto.getIssuerCode(),
                tossPayCardDto.getAcquirerCode(),
                tossPayCardDto.getNumber(),
                tossPayCardDto.getInstallmentPlanMonths(),
                tossPayCardDto.getIsInterestFree(),
                tossPayCardDto.getInterestPayer(),
                tossPayCardDto.getApproveNo(),
                tossPayCardDto.getUseCardPoint(),
                tossPayCardDto.getCardType(),
                tossPayCardDto.getOwnerType(),
                tossPayCardDto.getAcquireStatus(),
                tossPayCardDto.getReceiptUrl(),
                tossPayCardDto.getAmount()
        );

        final TossPayCardEntity savedTossPayCardEntity = tossPayCardRepository.save(tossPayCardEntity);
        return TossPayCardDto.from(savedTossPayCardEntity);
    }
}
