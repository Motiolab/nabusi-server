package com.motiolab.nabusi_server.paymentPackage.onSitePay.application;

import com.motiolab.nabusi_server.paymentPackage.onSitePay.application.dto.OnSitePayDto;
import com.motiolab.nabusi_server.paymentPackage.onSitePay.domain.OnSitePayEntity;
import com.motiolab.nabusi_server.paymentPackage.onSitePay.domain.OnSitePayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnSitePayServiceImpl implements OnSitePayService{
    private final OnSitePayRepository onSitePayRepository;

    @Override
    public OnSitePayDto create(OnSitePayDto onSitePayDto) {
        final OnSitePayEntity onSitePayEntity = OnSitePayEntity.create(
                onSitePayDto.getPaymentId(),
                onSitePayDto.getDiscountRate(),
                onSitePayDto.getTotalPayValue(),
                onSitePayDto.getUnpaidValue(),
                onSitePayDto.getCardInstallment(),
                onSitePayDto.getCardPayValue(),
                onSitePayDto.getCashPayValue(),
                onSitePayDto.getPayerMemberId(),
                onSitePayDto.getPayeeMemberId(),
                onSitePayDto.getNote(),
                onSitePayDto.getPaymentDate(),
                onSitePayDto.getPaymentMethod(),
                onSitePayDto.getUpdateMemberId()
        );

        OnSitePayEntity savedOnSitePayEntity = onSitePayRepository.save(onSitePayEntity);
        return OnSitePayDto.from(savedOnSitePayEntity);
    }
}
