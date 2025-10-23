package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.adapter.out.infrastructure.PetInitiatedLetterGenerator;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.GeneratedLetterContent;
import com.rainbowletter.server.slack.application.domain.service.SlackErrorReportService;
import com.rainbowletter.server.user.application.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus.READY_TO_SEND;
import static com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus.SCHEDULED;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetInitiatedLetterRetryScheduler {

    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final PetInitiatedLetterGenerator petInitiatedLetterGenerator;
    private final LoadPetPort loadPetPort;
    private final SlackErrorReportService slackErrorReportService;
    private final PetInitiatedLetterSubmitter submitter;

    @Schedules({
        @Scheduled(cron = "0 5 20 * * MON,WED,FRI"),
        @Scheduled(cron = "0 15 20 * * MON,WED,FRI"),
        @Scheduled(cron = "0 25 20 * * MON,WED,FRI")
    })
    public void regeneratePetInitiatedLetters() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        List<PetInitiatedLetter> petInitiatedLetters =
            petInitiatedLetterJpaRepository.findAllByStatusAndCreatedAtBetween(SCHEDULED, startOfDay, endOfDay);

        if (petInitiatedLetters.isEmpty()) {
            log.info("AI 생성 재시도할 선편지가 없습니다.");
            return;
        }

        for (PetInitiatedLetter letter : petInitiatedLetters) {
            try {
                Pet pet = loadPetPort.loadPetByIdAndUserId(
                    new Pet.PetId(letter.getPetId()), new User.UserId(letter.getUserId())
                );

                GeneratedLetterContent content = petInitiatedLetterGenerator.generate(pet);
                letter.generate(content);

            } catch (Exception e) {
                letter.markAsFailed();
                log.error("선편지 AI 생성 재시도 실패, letterId={}", letter.getId(), e);
                slackErrorReportService.sendGeneratePetLetterErrorReportToSlack(letter.getId(), e);
            }

            petInitiatedLetterJpaRepository.save(letter);
        }

    }

    @Schedules({
        @Scheduled(cron = "0 10 20 * * MON,WED,FRI"),
        @Scheduled(cron = "0 20 20 * * MON,WED,FRI"),
        @Scheduled(cron = "0 30 20 * * MON,WED,FRI")
    })
    public void retrySendPetInitiatedLetters() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        List<PetInitiatedLetter> petInitiatedLetters =
            petInitiatedLetterJpaRepository.findAllByStatusAndCreatedAtBetween(READY_TO_SEND, startOfDay, endOfDay);

        if (petInitiatedLetters.isEmpty()) {
            log.info("발송 재시도할 선편지가 없습니다.");
            return;
        }

        petInitiatedLetters.forEach(submitter::submit);
    }

}
