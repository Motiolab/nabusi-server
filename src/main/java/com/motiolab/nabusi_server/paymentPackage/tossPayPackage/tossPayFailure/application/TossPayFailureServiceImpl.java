package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.application;

import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.application.dto.TossPayFailureDto;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.domain.TossPayFailureEntity;
import com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPayFailure.domain.TossPayFailureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossPayFailureServiceImpl implements TossPayFailureService {
    private final TossPayFailureRepository tossPayFailureRepository;

    @Override
    public TossPayFailureDto create(TossPayFailureDto tossPayFailureDto) {
        final TossPayFailureEntity tossPayFailureEntity = TossPayFailureEntity.create(
                tossPayFailureDto.getCode(),
                tossPayFailureDto.getMessage()
        );;
        final TossPayFailureEntity savedTossPayFailureEntity = tossPayFailureRepository.save(tossPayFailureEntity);
        return TossPayFailureDto.from(savedTossPayFailureEntity);

    }
}
