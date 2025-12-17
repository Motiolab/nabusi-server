package com.positivehotel.nabusi_server.paymentPackage.payment.application;

import com.positivehotel.nabusi_server.paymentPackage.onSitePay.application.OnSitePayService;
import com.positivehotel.nabusi_server.paymentPackage.onSitePay.application.dto.OnSitePayDto;
import com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.PaymentDto;
import com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.request.CreateOnSitePayUnpaidAdminRequestV1;
import com.positivehotel.nabusi_server.paymentPackage.payment.enums.PaymentStatusEnum;
import com.positivehotel.nabusi_server.ticketPackage.enums.PaymentMethodEnum;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.WellnessTicketIssuanceService;
import com.positivehotel.nabusi_server.ticketPackage.wellnessTicketIssuance.application.dto.WellnessTicketIssuanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class PaymentAdminServiceImpl implements PaymentAdminService{
    private final PaymentService paymentService;
    private final OnSitePayService onSitePayService;
    private final WellnessTicketIssuanceService wellnessTicketIssuanceService;

    @Transactional
    @Override
    public void createOnSitePayUnpaid(CreateOnSitePayUnpaidAdminRequestV1 createOnSitePayUnpaidAdminRequestV1) {
        final OnSitePayDto onSitePayDto = OnSitePayDto.builder()
                .paymentId(createOnSitePayUnpaidAdminRequestV1.getPaymentId())
                .totalPayValue(createOnSitePayUnpaidAdminRequestV1.getTotalPayValue())
                .unpaidValue(createOnSitePayUnpaidAdminRequestV1.getUnpaidValue())
                .cardInstallment(createOnSitePayUnpaidAdminRequestV1.getCardInstallment())
                .cardPayValue(createOnSitePayUnpaidAdminRequestV1.getCardPayValue())
                .cashPayValue(createOnSitePayUnpaidAdminRequestV1.getCashPayValue())
                .payerMemberId(createOnSitePayUnpaidAdminRequestV1.getPayerMemberId())
                .payeeMemberId(createOnSitePayUnpaidAdminRequestV1.getPayeeMemberId())
                .note(createOnSitePayUnpaidAdminRequestV1.getNote())
                .updateMemberId(createOnSitePayUnpaidAdminRequestV1.getActionMemberId())
                .paymentDate(ZonedDateTime.now())
                .paymentMethod(PaymentMethodEnum.ON_SITE)
                .build();

        final OnSitePayDto savedOnSitePayDto = onSitePayService.create(onSitePayDto);

        final PaymentDto paymentDto = PaymentDto.builder()
                .memberId(savedOnSitePayDto.getPayerMemberId())
                .onSitePayId(savedOnSitePayDto.getId())
                .status(createOnSitePayUnpaidAdminRequestV1.getUnpaidValue() > 0 ? PaymentStatusEnum.PARTIALLY_PAID : PaymentStatusEnum.PAID)
                .build();
        final PaymentDto savedPaymentDto = paymentService.create(paymentDto);

        final WellnessTicketIssuanceDto wellnessTicketIssuanceDto = wellnessTicketIssuanceService.getById(createOnSitePayUnpaidAdminRequestV1.getWellnessTicketIssuanceId());
        wellnessTicketIssuanceDto.setPaymentId(savedPaymentDto.getId());
        wellnessTicketIssuanceDto.setUnpaidValue(createOnSitePayUnpaidAdminRequestV1.getUnpaidValue());
        wellnessTicketIssuanceService.update(wellnessTicketIssuanceDto);
    }
}
