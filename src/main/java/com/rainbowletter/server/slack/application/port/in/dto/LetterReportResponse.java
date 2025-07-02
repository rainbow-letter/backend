package com.rainbowletter.server.slack.application.port.in.dto;

import java.time.LocalDateTime;

public record LetterReportResponse(
    Long totalLetters,
    Long inspectionPending,
    Long replySent,
    Long replyFailed,
    LocalDateTime letterStartTime,
    LocalDateTime letterEndTime
) {
    public String inspectionPendingPercentage() {
        return calculatePercentage(inspectionPending);
    }

    public String replySentPercentage() {
        return calculatePercentage(replySent);
    }

    public String replyFailedPercentage() {
        return calculatePercentage(replyFailed);
    }

    private String calculatePercentage(Long value) {
        if (totalLetters == 0L) return "0.0%";
        return String.format("%.2f%%", value * 100.0 / totalLetters);
    }
}