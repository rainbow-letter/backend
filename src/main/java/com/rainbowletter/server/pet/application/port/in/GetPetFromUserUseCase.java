package com.rainbowletter.server.pet.application.port.in;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.pet.application.port.in.dto.PetResponse;

public interface GetPetFromUserUseCase {

    PetResponse getPetFromUser(GetPetFromUserQuery query);

    record GetPetFromUserQuery(String email, PetId petId) {

    }

}
