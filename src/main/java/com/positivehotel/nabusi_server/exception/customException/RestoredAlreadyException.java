package com.positivehotel.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoredAlreadyException extends RuntimeException {
    private String domain;
    private Long id;

    public RestoredAlreadyException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " restored already, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public RestoredAlreadyException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public RestoredAlreadyException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public RestoredAlreadyException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}