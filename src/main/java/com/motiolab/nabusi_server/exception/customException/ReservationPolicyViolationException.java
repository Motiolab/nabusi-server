package com.motiolab.nabusi_server.exception.customException;

public class ReservationPolicyViolationException extends RuntimeException {
    public ReservationPolicyViolationException(String message) {
        super(message);
    }
}
