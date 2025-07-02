package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.DeletePetUseCase;
import com.rainbowletter.server.pet.application.port.out.DeletePetPort;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class DeletePetService implements DeletePetUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final DeletePetPort deletePetPort;
    private final PublishDomainEventPort publishDomainEventPort;

    @Override
    public void deletePet(final DeletePetByIdCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.email());
        final Pet pet = loadPetPort.loadPetByIdAndUserId(command.petId(), user.getId());
        pet.delete();
        publishDomainEventPort.publish(pet);
        deletePetPort.deletePet(pet);
    }

    @Override
    public void deletePetsByUserId(
        final DeletePetByUserIdCommand command,
        final Consumer<Pet> logEvent
    ) {
        final List<Pet> pets = loadPetPort.loadPetsByUserId(command.userId());
        for (final Pet pet : pets) {
            pet.delete();
            publishDomainEventPort.publish(pet);
            logEvent.accept(pet);
        }
        deletePetPort.deleteAll(pets);
    }

}
