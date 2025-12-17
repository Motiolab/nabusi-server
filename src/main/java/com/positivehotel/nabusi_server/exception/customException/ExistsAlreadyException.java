package com.positivehotel.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistsAlreadyException extends RuntimeException {
    private String domain;
    private Long id;

    public ExistsAlreadyException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " exists already, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public ExistsAlreadyException(Class<?> domain, String name, Long centerId) {
        super(domain.getSimpleName() + " exists already, name: " + name + ", centerId: " + centerId);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public ExistsAlreadyException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public ExistsAlreadyException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public ExistsAlreadyException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}