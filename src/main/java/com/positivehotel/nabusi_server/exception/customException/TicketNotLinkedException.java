package com.positivehotel.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketNotLinkedException extends RuntimeException {
    private String domain;
    private Long id;

    public TicketNotLinkedException(Class<?> domain, Long id) {
        super(domain.getSimpleName() + " is multiple Data, id: " + id);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public TicketNotLinkedException(String message, Class<?> domain, Long id) {
        super(message);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public TicketNotLinkedException(String message, Throwable cause, Class<?> domain, Long id) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }

    public TicketNotLinkedException(Throwable cause, Class<?> domain, Long id) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.id = id;
    }
}