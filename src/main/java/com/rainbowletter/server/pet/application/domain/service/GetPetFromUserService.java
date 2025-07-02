package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.GetPetFromUserUseCase;
import com.rainbowletter.server.pet.application.port.in.dto.PetResponse;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPetFromUserService implements GetPetFromUserUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;

    @Override
    public PetResponse getPetFromUser(final GetPetFromUserQuery query) {
        final User user = loadUserPort.loadUserByEmail(query.email());
        final Pet pet = loadPetPort.loadPetByIdAndUserId(query.petId(), user.getId());
        return PetResponse.from(pet);
    }

}
