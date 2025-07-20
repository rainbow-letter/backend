package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterDetailResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.RetrievePetInitiatedLettersRequest;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetInitiatedLetterService {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;

    public Page<PetInitiatedLetterResponse> getPetInitiatedLetters(
        RetrievePetInitiatedLettersRequest request,
        Pageable pageable
    ) {
        return petInitiatedLetterPersistenceAdapter.getPetInitiatedLetters(request, pageable);
    }

    public PetInitiatedLetterDetailResponse getPetInitiatedLetterDetail(Long letterId, Long userId, Long petId) {
        User user = loadUserPort.loadUserById(new User.UserId(userId));
        Pet pet = loadPetPort.loadPetByIdAndUserId(new Pet.PetId(petId), user.getId());
        return null;
    }
}
