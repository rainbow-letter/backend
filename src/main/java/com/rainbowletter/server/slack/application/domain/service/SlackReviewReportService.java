package com.rainbowletter.server.slack.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.PetInitiatedLetterReportResponse;
import com.rainbowletter.server.slack.adapter.out.SlackReviewClient;
import com.rainbowletter.server.slack.application.port.in.dto.LetterReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackReviewReportService {

    private final SlackMessageFormatter slackMessageFormatter;
    private final SlackReviewClient slackReviewClient;

    public void sendReportToSlack(String message) {
        try {
            slackReviewClient.sendSlackMessage(Map.of("text", message));
            log.info("Slack 메시지 전송 성공");
        } catch (Exception e) {
            log.error("Slack 메시지 전송 실패: {}", e.getMessage(), e);
        }
    }

    public void sendDailyLetterReportToSlack(LetterReportResponse report) {
        String message = slackMessageFormatter.formatDailyLetterReport(report);
        sendReportToSlack(message);
    }

    public void sendDailyPetInitiatedLetterReportToSlack(PetInitiatedLetterReportResponse report) {
        String message = slackMessageFormatter.formatDailyPetInitiatedLetterReport(report);
        sendReportToSlack(message);
    }
}