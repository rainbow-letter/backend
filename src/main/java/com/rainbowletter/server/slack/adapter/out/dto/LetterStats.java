package com.rainbowletter.server.slack.adapter.out.dto;

public record LetterStats(
    Long totalLetters,
    Long inspectionPending,
    Long replySent,
    Long replyFailed
) {
}