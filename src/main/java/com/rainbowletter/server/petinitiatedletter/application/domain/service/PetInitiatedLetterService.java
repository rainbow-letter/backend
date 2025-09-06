package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.*;
import com.rainbowletter.server.petinitiatedletter.adapter.out.infrastructure.PetInitiatedLetterGenerator;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.SubmitPetInitiatedLetterEvent;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.GeneratedLetterContent;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetInitiatedLetterService {

    private final TimeHolder timeHolder;
    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;
    private final PetInitiatedLetterGenerator petInitiatedLetterGenerator;
    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Page<PetInitiatedLetterResponse> getPetInitiatedLetters(
        RetrievePetInitiatedLettersRequest request,
        Pageable pageable
    ) {
        return petInitiatedLetterPersistenceAdapter.getPetInitiatedLetters(request, pageable);
    }

    @Transactional(readOnly = true)
    public PetInitiatedLetterForAdminResponse getPetInitiatedLetterDetailForAdmin(Long letterId, Long userId, Long petId) {
        PetInitiatedLetterForAdminResponse partialResponse =
            petInitiatedLetterPersistenceAdapter.getPetInitiatedLetterDetailForAdmin(letterId, userId, petId);

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

    @Transactional
    public void updatePetInitiatedLetter(Long letterId, PetInitiatedLetterUpdateRequest request) {
        PetInitiatedLetter letter = getPetInitiatedLetter(letterId);

        letter.update(request.promptType(), request.summary(), request.content());
    }

    @Transactional
    public void regeneratePetInitiatedLetter(Long letterId) {
        PetInitiatedLetter letter = getPetInitiatedLetter(letterId);

        Pet pet = loadPetPort.loadPetByIdAndUserId(new Pet.PetId(letter.getPetId()), new User.UserId(letter.getUserId()));

        GeneratedLetterContent generatedLetterContent = petInitiatedLetterGenerator.generate(pet);

        letter.generate(generatedLetterContent);
    }

    @Transactional
    public void submitPetInitiatedLetter(Long letterId) {
        PetInitiatedLetter letter = getPetInitiatedLetter(letterId);
        letter.submit(timeHolder.currentTime());
        eventPublisher.publishEvent(new SubmitPetInitiatedLetterEvent(letter));

    }

    private PetInitiatedLetter getPetInitiatedLetter(Long letterId) {
        return petInitiatedLetterJpaRepository.findById(letterId)
            .orElseThrow(() -> new RainbowLetterException("해당 선편지를 찾을 수 없습니다.", "선편지 ID : " + letterId));
    }

    @Transactional(readOnly = true)
    public PetInitiatedLetterWithPetNameResponse getLetterByShareLink(UUID shareLink) {
        return petInitiatedLetterPersistenceAdapter.getLetterByShareLink(shareLink);
    }

    @Transactional(readOnly = true)
    public PetInitiatedLetterSummary getPetInitiatedLetterDetail(String email, Long letterId) {
        User user = loadUserPort.loadUserByEmail(email);
        return petInitiatedLetterPersistenceAdapter.getPetInitiatedLetterDetail(user.getId().value(), letterId);
    }
}
