package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase;
import com.rainbowletter.server.pet.application.port.in.dto.PetExcludeFavoriteResponse;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPetByLetterService implements GetPetByLetterUseCase {

    private final LoadPetPort loadPetPort;

    @Override
    public PetExcludeFavoriteResponse getPetByLetterId(final GetPetByLetterIdQuery query) {
        final Pet pet = loadPetPort.loadPetByLetterId(query.letterId());
        return PetExcludeFavoriteResponse.from(pet);
    }

    @Override
    public PetExcludeFavoriteResponse getPetByShareLink(final GetPetByShareLinkQuery query) {
        final Pet pet = loadPetPort.loadPetByShareLink(query.shareLink());
        return PetExcludeFavoriteResponse.from(pet);
    }

}
