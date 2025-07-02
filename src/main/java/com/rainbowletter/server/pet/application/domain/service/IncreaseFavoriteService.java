package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.IncreaseFavoriteUseCase;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.pet.application.port.out.UpdatePetStatePort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class IncreaseFavoriteService implements IncreaseFavoriteUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final UpdatePetStatePort updatePetStatePort;

    @Override
    public void increaseFavorite(final IncreaseFavoriteCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.email());
        final Pet pet = loadPetPort.loadPetByIdAndUserId(command.petId(), user.getId());
        pet.increaseFavorite(LocalDateTime.now());
        updatePetStatePort.updatePet(pet);
    }

}
