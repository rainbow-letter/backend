package com.rainbowletter.server.pet.application.port.out;

import com.rainbowletter.server.pet.application.domain.model.Pet;
import java.util.List;

public interface DeletePetPort {

    void deletePet(Pet pet);

    void deleteAll(List<Pet> pets);

}
