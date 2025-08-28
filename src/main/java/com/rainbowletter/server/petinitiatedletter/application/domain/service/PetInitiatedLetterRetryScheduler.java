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
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    @Scheduled(cron = "0 5 20 * * MON,WED,FRI")
    public void retryFailedLettersAt20_05() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atTime(19, 29);
        LocalDateTime end   = today.atTime(20, 0);

        List<PetInitiatedLetter> petInitiatedLetters =
            petInitiatedLetterJpaRepository.findAllByStatusAndCreatedAtBetween(SCHEDULED, start, end);

        if (petInitiatedLetters.isEmpty()) {
            log.info("오늘 발송할 선편지가 없습니다.");
            return;
        }

        for (PetInitiatedLetter letter : petInitiatedLetters) {
            try {
                Pet pet = loadPetPort.loadPetByIdAndUserId(
                    new Pet.PetId(letter.getPetId()), new User.UserId(letter.getUserId())
                );

                GeneratedLetterContent generatedLetterContent = petInitiatedLetterGenerator.generate(pet);
                letter.generate(generatedLetterContent);

            } catch (Exception e) {
                letter.markAsFailed();
                log.error("선편지 AI 생성 재시도 실패, letterId={}", letter.getId(), e);
                slackErrorReportService.sendGeneratePetLetterErrorReportToSlack(letter.getId(), e);
            }
        }

    }

}
