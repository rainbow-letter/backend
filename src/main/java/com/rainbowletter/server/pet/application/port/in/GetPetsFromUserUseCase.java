package com.rainbowletter.server.pet.application.port.in;

import com.rainbowletter.server.pet.application.port.in.dto.PetsResponse;

public interface GetPetsFromUserUseCase {

    PetsResponse getPetsFromUser(GetPetsFromUserQuery query);

    record GetPetsFromUserQuery(String email) {

    }

}
