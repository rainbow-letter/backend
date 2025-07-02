package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.CreatePetCommand;
import com.rainbowletter.server.pet.application.port.in.CreatePetUseCase;
import com.rainbowletter.server.pet.application.port.out.CreatePetPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class CreatePetService implements CreatePetUseCase {

    private final LoadUserPort loadUserPort;
    private final CreatePetPort createPetPort;

    @Override
    public Long createPet(final CreatePetCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.getEmail());
        final LocalDateTime currentTime = LocalDateTime.now();
        final Pet pet = Pet.withoutId(
            user.getId(),
            command.getName(),
            command.getSpecies(),
            command.getOwner(),
            command.getImage(),
            command.getPersonalities(),
            command.getDeathAnniversary(),
            currentTime,
            currentTime,
            currentTime
        );
        return createPetPort.createPet(pet)
            .getId()
            .value();
    }

}
