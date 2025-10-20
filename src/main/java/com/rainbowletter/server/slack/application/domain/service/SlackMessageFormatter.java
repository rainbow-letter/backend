package com.rainbowletter.server.slack.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.PetInitiatedLetterReportResponse;
import com.rainbowletter.server.slack.application.port.in.dto.LetterReportResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class SlackMessageFormatter {

    public String formatDailyLetterReport(LetterReportResponse report) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return String.format("""
            📢 *Daily Letter Report*
            1. 기간: %s ~ %s
            2. 총 편지 개수: %d
            3. 검수대기: %d (%s)
            4. 발송완료: %d (%s)
            5. 발송실패: %d (%s)
            """,
            report.letterStartTime().format(formatter),
            report.letterEndTime().format(formatter),
            report.totalLetters(),
            report.inspectionPending(), report.inspectionPendingPercentage(),
            report.replySent(), report.replySentPercentage(),
            report.replyFailed(), report.replyFailedPercentage()
        );
    }

    public String formatDailyPetInitiatedLetterReport(PetInitiatedLetterReportResponse report) {
        return String.format("""
                🔊 *Daily Pet-Initiated-Letter Report*
                1. 날짜: %s
                2. 총 선편지 개수: %d
                3. 생성예정: %d (%s)
                4. 발송대기: %d (%s)
                5. 발송완료: %d (%s)
                """,
            report.date(),
            report.totalLetters(),
            report.scheduled(), report.scheduledPercentage(),
            report.readyToSend(), report.readyToSendPercentage(),
            report.sent(), report.sentPercentage()
        );
    }

    public String formatImageUploadErrorReport(String filePath, Throwable exception) {
        String reason = exception.getMessage() != null ? exception.getMessage() : "알 수 없는 에러";

        return String.join("\n",
            "❌ *이미지 업로드 실패*",
            "- 경로: `" + filePath + "`",
            "- 사유: " + reason
        );
    }

    public String formatGeneratePetLetterErrorReport(Long letterId, Throwable exception) {
        String reason = exception.getMessage() != null ? exception.getMessage() : "알 수 없는 에러";

        return String.join("\n",
            "❌ *선편지 AI 생성 실패*",
            "- 선편지 ID : `" + letterId + "`",
            "- 사유 : " + reason
        );
    }

    public String formatSubmitPetLetterErrorReport(Long letterId, Throwable exception) {
        String reason = exception.getMessage() != null ? exception.getMessage() : "알 수 없는 에러";

        return String.join("\n",
            "❌ *선편지 발송 실패*",
            "- 선편지 ID : `" + letterId + "`",
            "- 사유 : " + reason
        );
    }

    public String formatClientErrorReport(String message, String url, String email) {
        return String.join("\n",
            "❌ *클라이언트 에러 전송*",
            "- 에러 메세지 : `" + message + "`",
            "- URL : " + url + "'",
            "- 유저 이메일 : " + email
        );
    }

}