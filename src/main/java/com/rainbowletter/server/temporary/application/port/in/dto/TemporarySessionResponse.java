package com.rainbowletter.server.temporary.application.port.in.dto;

import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import java.util.UUID;

public record TemporarySessionResponse(UUID sessionId) {

    public static TemporarySessionResponse from(final Temporary temporary) {
        return new TemporarySessionResponse(temporary.getSessionId());
    }

}
