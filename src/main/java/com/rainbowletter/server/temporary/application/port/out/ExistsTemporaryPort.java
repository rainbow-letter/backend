package com.rainbowletter.server.temporary.application.port.out;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;

public interface ExistsTemporaryPort {

    boolean existsEmailAndPetId(String email, PetId petId);

}
