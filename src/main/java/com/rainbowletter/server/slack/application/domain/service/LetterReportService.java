package com.rainbowletter.server.slack.application.domain.service;

import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.slack.adapter.out.dto.LetterStats;
import com.rainbowletter.server.slack.application.port.in.dto.LetterReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LetterReportService {

    private final LoadLetterPort loadLetterPort;
    private final SlackReviewReportService slackReviewReportService;

    public void report(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime letterStartTime = startDate != null ? startDate : now.minusDays(1).withHour(10).withMinute(0).withSecond(0);
        LocalDateTime letterEndTime = endDate != null ? endDate : now.withHour(9).withMinute(59).withSecond(59);

        LetterStats stats = loadLetterPort.getLetterReportByCreatedAtBetween(letterStartTime, letterEndTime);

        LetterReportResponse report = new LetterReportResponse(
            stats.totalLetters(),
            stats.inspectionPending(),
            stats.replySent(),
            stats.replyFailed(),
            letterStartTime,
            letterEndTime
        );

        slackReviewReportService.sendDailyLetterReportToSlack(report);
    }
}