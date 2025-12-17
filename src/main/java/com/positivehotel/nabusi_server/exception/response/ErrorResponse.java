package com.positivehotel.nabusi_server.exception.response;

import lombok.Builder;

@Builder
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
}
