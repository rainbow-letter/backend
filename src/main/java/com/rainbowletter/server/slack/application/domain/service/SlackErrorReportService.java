package com.rainbowletter.server.slack.application.domain.service;

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

    public void sendErrorReportToSlack(String filePath, Throwable exception) {
        String message = slackMessageFormatter.formatImageUploadErrorReport(filePath, exception);

        try {
            slackErrorClient.sendSlackMessage(Map.of("text", message));
            log.info("슬랙 에러 메시지 전송 성공");
        } catch (Exception e) {
            log.error("슬랙 에러 메시지 전송 실패: {}", e.getMessage() != null ? e.getMessage() : "알 수 없는 에러");
        }
    }

}