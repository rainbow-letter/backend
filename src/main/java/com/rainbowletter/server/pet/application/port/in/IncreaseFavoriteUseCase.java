package com.rainbowletter.server.pet.application.port.in;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;

public interface IncreaseFavoriteUseCase {

    void increaseFavorite(IncreaseFavoriteCommand command);

    record IncreaseFavoriteCommand(String email, PetId petId) {

    }

}
