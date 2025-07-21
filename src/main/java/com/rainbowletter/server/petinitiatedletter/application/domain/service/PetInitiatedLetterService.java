package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterForAdminResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLettersForAdminResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.RetrievePetInitiatedLettersRequest;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetInitiatedLetterService {

    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;

    @Transactional(readOnly = true)
    public Page<PetInitiatedLetterResponse> getPetInitiatedLetters(
        RetrievePetInitiatedLettersRequest request,
        Pageable pageable
    ) {
        return petInitiatedLetterPersistenceAdapter.getPetInitiatedLetters(request, pageable);
    }

    @Transactional(readOnly = true)
    public PetInitiatedLetterForAdminResponse getPetInitiatedLetterDetail(Long letterId, Long userId, Long petId) {
        PetInitiatedLetterForAdminResponse partialResponse =
            petInitiatedLetterPersistenceAdapter.getPetInitiatedLetterDetail(letterId, userId, petId);

        List<PetInitiatedLettersForAdminResponse> petInitiatedLetterList =
            petInitiatedLetterPersistenceAdapter.getPetInitiatedLetterListByUserId(userId);

        return new PetInitiatedLetterForAdminResponse(
            partialResponse.userForAdminResponse(),
            (long) petInitiatedLetterList.size(),
            partialResponse.petForAdminResponse(),
            petInitiatedLetterList,
            partialResponse.petInitiatedLetterDetailResponse()
        );
    }
}
