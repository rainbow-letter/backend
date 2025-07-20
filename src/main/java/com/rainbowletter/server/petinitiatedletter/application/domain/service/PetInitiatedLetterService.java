package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.RetrievePetInitiatedLettersRequest;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetInitiatedLetterService {

    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;

    public Page<PetInitiatedLetterResponse> getPetInitiatedLetters(
        RetrievePetInitiatedLettersRequest request,
        Pageable pageable
    ) {
        return petInitiatedLetterPersistenceAdapter.getPetInitiatedLetters(request, pageable);
    }
}
