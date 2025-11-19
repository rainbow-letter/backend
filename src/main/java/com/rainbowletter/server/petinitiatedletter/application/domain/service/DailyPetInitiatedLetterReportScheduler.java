package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.PetInitiatedLetterReportResponse;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.PetInitiatedLetterStats;
import com.rainbowletter.server.slack.application.domain.service.SlackReviewReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DailyPetInitiatedLetterReportScheduler {

    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;
    private final SlackReviewReportService slackReviewReportService;

    @Scheduled(cron = "0 0 21 * * FRI")
    public void sendDailyPetInitiatedLetterReport() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        PetInitiatedLetterStats letterStats =
            petInitiatedLetterPersistenceAdapter.getPetInitiatedLetterReportByCreatedAtBetween(startOfDay, endOfDay);

        PetInitiatedLetterReportResponse report = new PetInitiatedLetterReportResponse(
            letterStats.totalLetters(),
            letterStats.scheduled(),
            letterStats.readyToSend(),
            letterStats.sent(),
            LocalDate.now()
        );

        slackReviewReportService.sendDailyPetInitiatedLetterReportToSlack(report);
    }

}
