package com.rainbowletter.server.slack.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DailyLetterReportScheduler {

    private final LetterReportService letterReportService;

    @Scheduled(cron = "0 10 10 * * *")
    public void sendDailyLetterReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(1).withHour(10).withMinute(0).withSecond(0);
        LocalDateTime endDate = now.withHour(9).withMinute(59).withSecond(59);

        letterReportService.report(startDate, endDate);
    }
}