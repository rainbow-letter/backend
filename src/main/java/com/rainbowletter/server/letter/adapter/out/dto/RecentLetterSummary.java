package com.rainbowletter.server.letter.adapter.out.dto;

public record RecentLetterSummary(
    Long letterId,
    String letterContent
) {
}