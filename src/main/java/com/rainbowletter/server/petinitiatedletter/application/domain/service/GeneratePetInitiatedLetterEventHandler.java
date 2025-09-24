package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.adapter.out.infrastructure.PetInitiatedLetterGenerator;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.GeneratePetInitiatedLetterEvent;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.GeneratedLetterContent;
import com.rainbowletter.server.slack.application.domain.service.SlackErrorReportService;
import com.rainbowletter.server.user.application.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneratePetInitiatedLetterEventHandler {

    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final LoadPetPort loadPetPort;
    private final PetInitiatedLetterGenerator petInitiatedLetterGenerator;
    private final SlackErrorReportService slackErrorReportService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleGeneratePetInitiatedLetters(GeneratePetInitiatedLetterEvent event) {
        for (Long letterId : event.letterIds()) {
            PetInitiatedLetter letter = petInitiatedLetterJpaRepository.findById(letterId)
                .orElseThrow(() -> new RainbowLetterException("선편지를 찾을 수 없습니다: " + letterId));

            try {
                Pet pet = loadPetPort.loadPetByIdAndUserId(
                    new Pet.PetId(letter.getPetId()), new User.UserId(letter.getUserId())
                );

                GeneratedLetterContent generatedLetterContent = petInitiatedLetterGenerator.generate(pet);
                letter.generate(generatedLetterContent);

            } catch (Exception e) {
                letter.markAsFailed();
                log.error("선편지 AI 생성 실패, letterId={}", letter.getId(), e);
                slackErrorReportService.sendGeneratePetLetterErrorReportToSlack(letter.getId(), e);
            }
        }
    }

}
