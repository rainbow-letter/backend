package com.rainbowletter.server.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionLogUtils {

    private static final String MESSAGE_FORMAT = "[{}] '{}' {} {}";
    private static final String INCLUDE_FIELD_MESSAGE_FORMAT = "[{}] '{}' {} {} - [{}]";

    public static void warn(final HttpStatus status, final String path, final String message) {
        log.warn(MESSAGE_FORMAT, status.name(), path, status.value(), message);
    }

    public static void warn(
        final HttpStatus status,
        final String path,
        final String message,
        final Object field
    ) {
        log.warn(INCLUDE_FIELD_MESSAGE_FORMAT, status.name(), path, status.value(), message, field);
    }

    public static void error(
        final HttpStatus status,
        final String path,
        final String message,
        final Exception exception
    ) {
        log.error(MESSAGE_FORMAT, status.name(), path, status.value(), message);
        log.error("Caused by: ", exception);
    }

}
