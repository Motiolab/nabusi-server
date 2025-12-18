package com.motiolab.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotMemberException extends RuntimeException {
    private String domain;
    private Long id;

    public NotMemberException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " is not member, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NotMemberException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NotMemberException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public NotMemberException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}