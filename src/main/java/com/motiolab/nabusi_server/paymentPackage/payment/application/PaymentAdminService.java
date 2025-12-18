package com.motiolab.nabusi_server.paymentPackage.payment.application;

import com.motiolab.nabusi_server.paymentPackage.payment.application.dto.request.CreateOnSitePayUnpaidAdminRequestV1;

public interface PaymentAdminService {
    void createOnSitePayUnpaid(CreateOnSitePayUnpaidAdminRequestV1 createOnSitePayUnpaidAdminRequestV1);
}
