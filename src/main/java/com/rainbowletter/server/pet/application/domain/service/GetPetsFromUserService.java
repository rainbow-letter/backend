package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.GetPetsFromUserUseCase;
import com.rainbowletter.server.pet.application.port.in.dto.PetsResponse;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPetsFromUserService implements GetPetsFromUserUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;

    @Override
    public PetsResponse getPetsFromUser(final GetPetsFromUserQuery query) {
        final User user = loadUserPort.loadUserByEmail(query.email());
        final List<Pet> pets = loadPetPort.loadPetsByUserId(user.getId());
        return PetsResponse.from(pets);
    }

}
