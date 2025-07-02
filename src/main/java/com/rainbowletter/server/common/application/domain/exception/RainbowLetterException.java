package com.rainbowletter.server.common.application.domain.exception;

import lombok.Getter;

@Getter
public class RainbowLetterException extends RuntimeException {

    private final transient Object resource;

    public RainbowLetterException(final String message) {
        super(message);
        this.resource = null;
    }

    public RainbowLetterException(final String message, final Object resource) {
        super(message.formatted(resource));
        this.resource = resource;
    }

    public RainbowLetterException(final String message, final Throwable cause) {
        super(message, cause);
        this.resource = null;
    }

}
