package com.rainbowletter.server.letter.application.port.out;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;

public interface CountLetterPort {

    Long countByUserId(UserId userId);

    Long countByPetId(PetId petId);

    Integer getLastNumber(String email, PetId petId);
}
