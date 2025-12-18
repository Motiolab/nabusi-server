package com.motiolab.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullReservationException extends RuntimeException {
    private String domain;
    private Long id;

    public FullReservationException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " is multiple Data, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public FullReservationException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public FullReservationException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public FullReservationException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}