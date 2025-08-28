package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.PetInitiatedLetterReportResponse;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.PetInitiatedLetterStats;
import com.rainbowletter.server.slack.application.domain.service.SlackReviewReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DailyPetInitiatedLetterReportScheduler {

    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;
    private final SlackReviewReportService slackReviewReportService;

    @Scheduled(cron = "0 10 20 * * MON,WED,FRI")
    public void sendDailyPetInitiatedLetterReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.withHour(19).withMinute(29).withSecond(0);
        LocalDateTime endDate = now.withHour(20).withMinute(19).withSecond(59);

        PetInitiatedLetterStats letterStats =
            petInitiatedLetterPersistenceAdapter.getPetInitiatedLetterReportByCreatedAtBetween(startDate, endDate);

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
