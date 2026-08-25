package com.innorun.learningmate.global.security;

import com.innorun.learningmate.global.exception.ServiceException;
import com.innorun.learningmate.global.security.jwt.JwtErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SecurityErrorResponseSender {

    public void send(HttpServletResponse response, JwtErrorCode errorCode) throws IOException {
        send(response, errorCode.toException());
    }

    public void send(HttpServletResponse response, ServiceException exception) throws IOException {
        response.setStatus(exception.getStatus().value());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(exception.getMessage());
    }
}
