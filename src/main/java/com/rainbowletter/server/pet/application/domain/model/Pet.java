package com.rainbowletter.server.pet.application.domain.model;

import com.rainbowletter.server.common.application.domain.model.AggregateRoot;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pet extends AggregateRoot {

    private final PetId id;
    private final UserId userId;

    private String name;
    private String species;
    private String owner;
    private String image;
    private List<String> personalities;
    private LocalDate deathAnniversary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @SuppressWarnings("java:S107")
    public static Pet withoutId(
        final UserId userId,
        final String name,
        final String species,
        final String owner,
        final String image,
        final List<String> personalities,
        final LocalDate deathAnniversary,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Pet(
            null,
            userId,
            name,
            species,
            owner,
            image,
            personalities,
            deathAnniversary,
            createdAt,
            updatedAt
        );
    }

    @SuppressWarnings("java:S107")
    public static Pet withId(
        final PetId id,
        final UserId userId,
        final String name,
        final String species,
        final String owner,
        final String image,
        final List<String> personalities,
        final LocalDate deathAnniversary,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Pet(
            id,
            userId,
            name,
            species,
            owner,
            image,
            personalities,
            deathAnniversary,
            createdAt,
            updatedAt
        );
    }

    public void update(
        final String name,
        final String species,
        final String owner,
        final String image,
        final List<String> personalities,
        final LocalDate deathAnniversary
    ) {
        this.name = name;
        this.species = species;
        this.owner = owner;
        this.image = image;
        this.personalities = personalities;
        this.deathAnniversary = deathAnniversary;
    }

    public boolean hasImage() {
        return StringUtils.hasText(image);
    }

    public void delete() {
        registerEvent(new DeletePetEvent(this));
    }

    public record PetId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
