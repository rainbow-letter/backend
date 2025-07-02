package com.rainbowletter.server.pet.application.port.in;

import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.util.function.Consumer;

public interface DeletePetUseCase {

    void deletePet(DeletePetByIdCommand command);

    void deletePetsByUserId(DeletePetByUserIdCommand command, Consumer<Pet> logEvent);

    record DeletePetByIdCommand(String email, PetId petId) {

    }

    record DeletePetByUserIdCommand(UserId userId) {

    }

}
