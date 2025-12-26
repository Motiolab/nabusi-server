package com.motiolab.nabusi_server.exception.customException;

public class PaymentFailureException extends RuntimeException {
    public PaymentFailureException(String message) {
        super(message);
    }
}
