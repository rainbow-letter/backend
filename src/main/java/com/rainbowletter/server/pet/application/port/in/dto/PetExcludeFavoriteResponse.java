package com.rainbowletter.server.pet.application.port.in.dto;

import com.rainbowletter.server.pet.application.domain.model.Pet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PetExcludeFavoriteResponse(
    Long id,
    Long userId,
    String name,
    String species,
    String owner,
    List<String> personalities,
    LocalDate deathAnniversary,
    String image,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static PetExcludeFavoriteResponse from(final Pet pet) {
        return new PetExcludeFavoriteResponse(
            pet.getId().value(),
            pet.getUserId().value(),
            pet.getName(),
            pet.getSpecies(),
            pet.getOwner(),
            pet.getPersonalities(),
            pet.getDeathAnniversary(),
            pet.getImage(),
            pet.getCreatedAt(),
            pet.getUpdatedAt()
        );
    }

}
