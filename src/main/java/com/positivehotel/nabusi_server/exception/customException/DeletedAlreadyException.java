package com.positivehotel.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletedAlreadyException extends RuntimeException {
    private String domain;
    private Long id;

    public DeletedAlreadyException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " deleted already, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public DeletedAlreadyException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public DeletedAlreadyException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public DeletedAlreadyException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}