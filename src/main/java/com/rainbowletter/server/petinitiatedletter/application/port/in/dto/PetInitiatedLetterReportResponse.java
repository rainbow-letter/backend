package com.rainbowletter.server.petinitiatedletter.application.port.in.dto;

import java.time.LocalDate;

public record PetInitiatedLetterReportResponse(
    Long totalLetters,
    Long scheduled,    // 생성예정
    Long readyToSend,  // 발송대기
    Long sent,         // 발송완료
    LocalDate date     // 보고 날짜
) {
    public String scheduledPercentage() {
        return calculatePercentage(scheduled);
    }

    public String readyToSendPercentage() {
        return calculatePercentage(readyToSend);
    }

    public String sentPercentage() {
        return calculatePercentage(sent);
    }

    private String calculatePercentage(Long value) {
        if (totalLetters == 0L) return "0.0%";
        return String.format("%.2f%%", value * 100.0 / totalLetters);
    }
}
