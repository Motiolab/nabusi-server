package com.positivehotel.nabusi_server.paymentPackage.payment.application;

import com.positivehotel.nabusi_server.paymentPackage.payment.application.dto.request.CreateOnSitePayUnpaidAdminRequestV1;

public interface PaymentAdminService {
    void createOnSitePayUnpaid(CreateOnSitePayUnpaidAdminRequestV1 createOnSitePayUnpaidAdminRequestV1);
}
