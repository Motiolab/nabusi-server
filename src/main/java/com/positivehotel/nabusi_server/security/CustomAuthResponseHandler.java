package com.positivehotel.nabusi_server.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthResponseHandler {
    public void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("error: " + message);
        log.warn("{} {}", HttpServletResponse.SC_UNAUTHORIZED, message);
    }
    public void forbidden(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("error: " + message);
        log.warn("{} {}", HttpServletResponse.SC_FORBIDDEN, message);
    }
}