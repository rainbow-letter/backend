package com.rainbowletter.server.pet.application.port.in.dto;

import com.rainbowletter.server.pet.application.domain.model.Pet;

import java.time.LocalDate;
import java.util.List;

public record PetSummary(
    Long id,
    String name,
    LocalDate deathAnniversary,
    String image,
    List<String> personalities,
    String owner
) {
    public static PetSummary from(final Pet pet) {
        return new PetSummary(
            pet.getId().value(),
            pet.getName(),
            pet.getDeathAnniversary(),
            pet.getImage(),
            pet.getPersonalities() != null ? pet.getPersonalities() : List.of(),
            pet.getOwner()
        );
    }
}