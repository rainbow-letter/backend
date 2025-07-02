package com.rainbowletter.server.slack.application.domain.service;

import com.rainbowletter.server.slack.adapter.out.SlackReviewClient;
import com.rainbowletter.server.slack.application.port.in.dto.LetterReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackReviewReportService {

    private final SlackMessageFormatter slackMessageFormatter;
    private final SlackReviewClient slackReviewClient;

    public void sendReportToSlack(LetterReportResponse report) {
        String message = slackMessageFormatter.formatDailyLetterReport(report);
        Map<String, String> payload = Map.of("text", message);

        try {
            slackReviewClient.sendSlackMessage(payload);
            log.info("Slack 메시지 전송 성공");
        } catch (Exception e) {
            log.error("Slack 메시지 전송 실패: {}", e.getMessage(), e);
        }
    }
}