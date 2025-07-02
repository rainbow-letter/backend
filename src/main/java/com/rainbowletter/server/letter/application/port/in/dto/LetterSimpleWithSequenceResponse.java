package com.rainbowletter.server.letter.application.port.in.dto;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterStatus;

import java.time.LocalDateTime;

public record LetterSimpleWithSequenceResponse(
        Long id,
        Long sequence,
        LocalDateTime createdAt,
        String summary,
        String content,
        LetterStatus letterStatus,
        String image
) {
    public static LetterSimpleWithSequenceResponse from(LetterSimpleResponse base, long sequence) {
        return new LetterSimpleWithSequenceResponse(
                base.id(), sequence, base.createdAt(), base.summary(), base.content(), base.letterStatus(), base.image()
        );
    }
}
