package com.rainbowletter.server.temporary.application.port.in.dto;

import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import java.util.UUID;

public record TemporaryCreateResponse(Long id, UUID sessionId) {

    public static TemporaryCreateResponse from(final Temporary temporary) {
        return new TemporaryCreateResponse(temporary.getId().value(), temporary.getSessionId());
    }

}
