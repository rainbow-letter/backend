package com.rainbowletter.server.temporary.application.port.in;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryExistsResponse;

public interface GetTemporaryExistsUseCase {

    TemporaryExistsResponse getExists(GetTemporaryExistsQuery query);

    record GetTemporaryExistsQuery(String email, PetId petId) {

    }

}
