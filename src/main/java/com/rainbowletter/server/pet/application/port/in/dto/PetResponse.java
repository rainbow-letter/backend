package com.rainbowletter.server.pet.application.port.in.dto;

import com.rainbowletter.server.pet.application.domain.model.Favorite;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PetResponse(
    Long id,
    Long userId,
    String name,
    String species,
    String owner,
    List<String> personalities,
    LocalDate deathAnniversary,
    String image,
    FavoriteResponse favorite,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static PetResponse from(final Pet pet) {
        return new PetResponse(
            pet.getId().value(),
            pet.getUserId().value(),
            pet.getName(),
            pet.getSpecies(),
            pet.getOwner(),
            pet.getPersonalities(),
            pet.getDeathAnniversary(),
            pet.getImage(),
            FavoriteResponse.from(pet.getFavorite()),
            pet.getCreatedAt(),
            pet.getUpdatedAt()
        );
    }

    public record FavoriteResponse(
        Long id,
        int total,
        int dayIncreaseCount,
        boolean canIncrease,
        LocalDateTime lastIncreasedAt
    ) {

        public static FavoriteResponse from(final Favorite favorite) {
            return new FavoriteResponse(
                favorite.getId().value(),
                favorite.getTotal(),
                favorite.getDayIncreaseCount(),
                favorite.isCanIncrease(),
                favorite.getLastIncreasedAt()
            );
        }

    }

}
