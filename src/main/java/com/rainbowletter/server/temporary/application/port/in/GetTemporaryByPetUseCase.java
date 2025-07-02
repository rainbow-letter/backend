package com.rainbowletter.server.temporary.application.port.in;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryResponse;

public interface GetTemporaryByPetUseCase {

    TemporaryResponse getTemporaryByPet(GetTemporaryByPetQuery query);

    record GetTemporaryByPetQuery(String email, PetId petId) {

    }

}
