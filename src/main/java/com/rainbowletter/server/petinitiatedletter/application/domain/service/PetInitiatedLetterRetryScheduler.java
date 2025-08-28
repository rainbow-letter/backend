package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.common.util.TimeHolder;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus.SCHEDULED;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetInitiatedLetterRetryScheduler {

    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final PetInitiatedLetterGenerator petInitiatedLetterGenerator;
    private final LoadPetPort loadPetPort;
    private final SlackErrorReportService slackErrorReportService;
    private final TimeHolder timeHolder;
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(cron = "0 5 20 * * *")
    public void regeneratePetInitiatedLetters() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.withHour(19).withMinute(29).withSecond(0);
        LocalDateTime end = now.withHour(20).withMinute(0).withSecond(0);

        List<PetInitiatedLetter> petInitiatedLetters =
            petInitiatedLetterJpaRepository.findAllByStatusAndCreatedAtBetween(SCHEDULED, start, end);

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

}
