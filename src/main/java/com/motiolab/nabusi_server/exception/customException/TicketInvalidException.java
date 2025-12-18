package com.motiolab.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketInvalidException extends RuntimeException {
    private String domain;
    private Long id;

    public TicketInvalidException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " is multiple Data, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public TicketInvalidException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public TicketInvalidException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public TicketInvalidException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}