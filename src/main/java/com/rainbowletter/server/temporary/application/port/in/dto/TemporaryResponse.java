package com.rainbowletter.server.temporary.application.port.in.dto;

import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record TemporaryResponse(
    Long id,
    Long userId,
    Long petId,
    UUID sessionId,
    String content,
    TemporaryStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static TemporaryResponse from(final Temporary temporary) {
        return new TemporaryResponse(
            temporary.getId().value(),
            temporary.getUserId().value(),
            temporary.getPetId().value(),
            temporary.getSessionId(),
            temporary.getContent(),
            temporary.getStatus(),
            temporary.getCreatedAt(),
            temporary.getUpdatedAt()
        );
    }

}
