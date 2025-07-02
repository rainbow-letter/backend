package com.rainbowletter.server.temporary.application.domain.model;

import com.rainbowletter.server.common.util.UuidHolder;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Temporary {

    private final TemporaryId id;
    private final UserId userId;
    private final PetId petId;

    private UUID sessionId;
    private String content;
    private TemporaryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @SuppressWarnings("java:S107")
    public static Temporary withId(
        final TemporaryId id,
        final UserId userId,
        final PetId petId,
        final UUID sessionId,
        final String content,
        final TemporaryStatus status,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Temporary(id, userId, petId, sessionId, content, status, createdAt, updatedAt);
    }

    public static Temporary withoutId(
        final UserId userId,
        final PetId petId,
        final UUID sessionId,
        final String content,
        final TemporaryStatus status,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Temporary(null, userId, petId, sessionId, content, status, createdAt, updatedAt);
    }

    public void updateContent(final String content) {
        this.content = content;
    }

    public void changeSessionId(final UuidHolder uuidHolder) {
        this.sessionId = uuidHolder.generate();
    }

    public void delete() {
        this.status = TemporaryStatus.DELETE;
    }

    public enum TemporaryStatus {
        SAVE, DELETE,
    }

    public record TemporaryId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
