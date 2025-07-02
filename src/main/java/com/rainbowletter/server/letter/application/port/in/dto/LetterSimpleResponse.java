package com.rainbowletter.server.letter.application.port.in.dto;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterStatus;

import java.time.LocalDateTime;

public record LetterSimpleResponse(
        Long id,
        LocalDateTime createdAt,
        String summary,
        String content,
        LetterStatus letterStatus,
        String image
) {
}