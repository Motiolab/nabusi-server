package com.motiolab.nabusi_server.paymentPackage.tossPayPackage.tossPay.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TossPayRepository extends JpaRepository<TossPayEntity, Long> {
    Optional<TossPayEntity> findByPaymentKey(String paymentKey);
}
