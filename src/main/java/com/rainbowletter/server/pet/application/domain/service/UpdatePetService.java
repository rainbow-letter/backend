package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.UpdatePetCommand;
import com.rainbowletter.server.pet.application.port.in.UpdatePetUseCase;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.pet.application.port.out.UpdatePetStatePort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdatePetService implements UpdatePetUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final UpdatePetStatePort updatePetStatePort;

    @Override
    public void updatePet(final UpdatePetCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.getEmail());
        final Pet pet = loadPetPort.loadPetByIdAndUserId(command.getId(), user.getId());
        pet.update(
            command.getName(),
            command.getSpecies(),
            command.getOwner(),
            command.getImage(),
            command.getPersonalities(),
            command.getDeathAnniversary()
        );
        updatePetStatePort.updatePet(pet);
    }

}
