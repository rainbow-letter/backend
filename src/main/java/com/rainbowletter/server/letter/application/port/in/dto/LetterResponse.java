package com.rainbowletter.server.letter.application.port.in.dto;

import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record LetterResponse(
    Long id,
    Long userId,
    Long petId,
    int number,
    String summary,
    String content,
    UUID shareLink,
    String image,
    LetterStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static LetterResponse from(final Letter letter) {
        return new LetterResponse(
            letter.getId().value(),
            letter.getUserId().value(),
            letter.getPetId().value(),
            letter.getNumber(),
            letter.getSummary(),
            letter.getContent(),
            letter.getShareLink(),
            letter.getImage(),
            letter.getStatus(),
            letter.getCreatedAt(),
            letter.getUpdatedAt()
        );
    }

}
