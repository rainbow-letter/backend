package com.rainbowletter.server.letter.application.port.out.dto;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterStatus;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyReadStatus;
import java.time.LocalDateTime;

public record LetterBoxResponse(
    Long id,
    int number,
    String summary,
    LetterStatus status,
    String petName,
    ReplyReadStatus readStatus,
    LocalDateTime createdAt
) {

}
