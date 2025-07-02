package com.rainbowletter.server.common.application.port.in;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, int status, LocalDateTime timestamp) {

    public static ErrorResponse of(final String message, final HttpStatus status) {
        return new ErrorResponse(message, status.value(), LocalDateTime.now());
    }

}
