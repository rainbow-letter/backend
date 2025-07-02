package com.rainbowletter.server.pet.application.port.in.dto;

import com.rainbowletter.server.pet.application.domain.model.Pet;
import java.util.List;

public record PetsResponse(List<PetResponse> pets) {

    public static PetsResponse from(final List<Pet> pets) {
        return new PetsResponse(
            pets.stream()
                .map(PetResponse::from)
                .toList()
        );
    }

}
