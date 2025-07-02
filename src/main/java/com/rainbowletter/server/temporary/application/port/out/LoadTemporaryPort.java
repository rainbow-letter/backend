package com.rainbowletter.server.temporary.application.port.out;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;

public interface LoadTemporaryPort {

    Temporary loadByEmailAndId(String email, TemporaryId id);

    Temporary loadByEmailAndPetId(String email, PetId petId);

}
