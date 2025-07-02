package com.rainbowletter.server.pet.application.port.out;

import com.rainbowletter.server.pet.application.domain.model.Pet;

public interface CreatePetPort {

    Pet createPet(Pet pet);

}
