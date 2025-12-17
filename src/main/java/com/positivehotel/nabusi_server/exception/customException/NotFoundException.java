package com.positivehotel.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    private String domain;
    private Long id;

    public NotFoundException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " is not found, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NotFoundException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NotFoundException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NotFoundException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}