package com.rainbowletter.server.slack.application.domain.service;

import com.rainbowletter.server.slack.adapter.in.web.dto.ClientErrorRequest;
import com.rainbowletter.server.slack.adapter.out.SlackErrorClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackErrorReportService {

    private final SlackErrorClient slackErrorClient;
    private final SlackMessageFormatter slackMessageFormatter;

    private void sendErrorReportToSlack(String message) {
        try {
            slackErrorClient.sendSlackMessage(Map.of("text", message));
            log.info("슬랙 에러 메시지 전송 성공");
        } catch (Exception e) {
            log.error("슬랙 에러 메시지 전송 실패: {}", e.getMessage() != null ? e.getMessage() : "알 수 없는 에러");
        }
    }

    public void sendImageUploadErrorReportToSlack(String filePath, Throwable exception) {
        String message = slackMessageFormatter.formatImageUploadErrorReport(filePath, exception);

        sendErrorReportToSlack(message);
    }

    public void sendGeneratePetLetterErrorReportToSlack(Long letterId, Throwable exception) {
        String message = slackMessageFormatter.formatGeneratePetLetterErrorReport(letterId, exception);

        sendErrorReportToSlack(message);
    }

    public void sendSubmitPetLetterErrorReportToSlack(Long letterId, Throwable exception) {
        String message = slackMessageFormatter.formatSubmitPetLetterErrorReport(letterId, exception);

        sendErrorReportToSlack(message);
    }

    public void reportClientError(ClientErrorRequest request, String email) {
        String message = slackMessageFormatter.formatClientErrorReport(request.message(), request.url(), email);

        sendErrorReportToSlack(message);
    }
}