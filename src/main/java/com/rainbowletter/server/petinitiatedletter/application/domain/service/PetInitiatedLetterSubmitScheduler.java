package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.SubmitPetInitiatedLetterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus.READY_TO_SEND;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetInitiatedLetterSubmitScheduler {

    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final TimeHolder timeHolder;
    private final ApplicationEventPublisher eventPublisher;

    @Async
    @Scheduled(cron = "0 0 20 * * *")
    @Transactional
    public void submitPetInitiatedLetter() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        List<PetInitiatedLetter> petInitiatedLetters =
            petInitiatedLetterJpaRepository.findAllByStatusAndCreatedAtBetween(READY_TO_SEND, startOfDay, endOfDay);

        if (petInitiatedLetters.isEmpty()) {
            log.info("오늘 발송할 선편지가 없습니다.");
            return;
        }

        for (PetInitiatedLetter letter : petInitiatedLetters) {
            letter.submit(timeHolder.currentTime());
            eventPublisher.publishEvent(new SubmitPetInitiatedLetterEvent(letter));
        }
        petInitiatedLetterJpaRepository.saveAll(petInitiatedLetters);
    }

}
