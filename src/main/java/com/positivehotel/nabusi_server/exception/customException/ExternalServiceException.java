package com.positivehotel.nabusi_server.exception.customException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalServiceException extends RuntimeException {
    private String domain;
    private String url;

    public ExternalServiceException(Class<?> domain, String url) {
        super(domain.getSimpleName() + " is error when call external service, url : " + url);
        this.domain = domain.getSimpleName();
        this.url = url;
    }

    public ExternalServiceException(String message, Class<?> domain, String url) {
        super(message);
        this.domain = domain.getSimpleName();
        this.url = url;
    }

    public ExternalServiceException(String message, Throwable cause, Class<?> domain, String url) {
        super(message, cause);
        this.domain = domain.getSimpleName();
        this.url = url;
    }

    public ExternalServiceException(Throwable cause, Class<?> domain, String url) {
        super(cause);
        this.domain = domain.getSimpleName();
        this.url = url;
    }
}