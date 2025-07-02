package com.rainbowletter.server.log.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class LogEventCommand {

    @NotNull
    Long resource;

    Long userId;

    @NotNull
    String category;

    @NotNull
    String event;

    @NotNull
    String message;

    public LogEventCommand(
        final Long resource,
        final Long userId,
        final String category,
        final String event,
        final String message
    ) {
        this.resource = resource;
        this.userId = userId;
        this.category = category;
        this.event = event;
        this.message = message;
        validate(this);
    }

}
