package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus.READY_TO_SEND;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetInitiatedLetterSubmitScheduler {

    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final PetInitiatedLetterSubmitter submitter;

    @Scheduled(cron = "0 0 20 * * FRI")
    public void submitPetInitiatedLetter() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        List<PetInitiatedLetter> petInitiatedLetters =
            petInitiatedLetterJpaRepository.findAllByStatusAndCreatedAtBetween(READY_TO_SEND, startOfDay, endOfDay);

        if (petInitiatedLetters.isEmpty()) {
            log.info("오늘 발송할 선편지가 없습니다.");
            return;
        }

        petInitiatedLetters.forEach(submitter::submit);
    }

}
