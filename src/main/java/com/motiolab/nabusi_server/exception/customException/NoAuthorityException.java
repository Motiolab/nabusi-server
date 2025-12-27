package com.motiolab.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoAuthorityException extends RuntimeException {
    private String domain;
    private Long id;

    public NoAuthorityException(Class<?> domain, Long id) {
        super("권한이 없습니다. domain: " + domain.getSimpleName() + ", id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NoAuthorityException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NoAuthorityException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NoAuthorityException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}
