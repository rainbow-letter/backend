package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase;
import com.rainbowletter.server.pet.application.port.in.dto.PetDetailResponse;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetPetByLetterService implements GetPetByLetterUseCase {

    private final LoadPetPort loadPetPort;

    @Override
    public PetDetailResponse getPetByLetterId(final GetPetByLetterIdQuery query) {
        final Pet pet = loadPetPort.loadPetByLetterId(query.letterId());
        return PetDetailResponse.from(pet);
    }

    @Override
    public PetDetailResponse getPetByShareLink(final GetPetByShareLinkQuery query) {
        final Pet pet = loadPetPort.loadPetByShareLink(query.shareLink());
        return PetDetailResponse.from(pet);
    }

}
