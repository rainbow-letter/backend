package com.rainbowletter.server.letter.application.domain.model;

import com.rainbowletter.server.common.application.domain.model.AggregateRoot;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Letter extends AggregateRoot {

    private final LetterId id;
    private final UserId userId;
    private final PetId petId;

    private String summary;
    private String content;
    private UUID shareLink;
    private String image;
    private Integer number;
    private LetterStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @SuppressWarnings("java:S107")
    public static Letter withId(
        final LetterId id,
        final UserId userId,
        final PetId petId,
        final String summary,
        final String content,
        final UUID shareLink,
        final String image,
        final Integer number,
        final LetterStatus status,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Letter(
            id,
            userId,
            petId,
            summary,
            content,
            shareLink,
            image,
            number,
            status,
            createdAt,
            updatedAt
        );
    }

    @SuppressWarnings("java:S107")
    public static Letter withoutId(
        final UserId userId,
        final PetId petId,
        final String summary,
        final String content,
        final UUID shareLink,
        final String image,
        final Integer number,
        final LetterStatus status,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Letter(
            null,
            userId,
            petId,
            summary,
            content,
            shareLink,
            image,
            number,
            status,
            createdAt,
            updatedAt
        );
    }

    public void createEvent() {
        registerEvent(new CreateLetterEvent(this));
    }

    public void receiveReply() {
        this.status = LetterStatus.RESPONSE;
    }

    public boolean hasImage() {
        return StringUtils.hasText(image);
    }

    public void delete() {
        registerEvent(new DeleteLetterEvent(this));
    }

    public void read() {
        this.status = LetterStatus.READ;
    }

    public enum LetterStatus {
        REQUEST, RESPONSE, READ
    }

    public record LetterId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
