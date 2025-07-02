package com.rainbowletter.server.letter.application.port.in.dto;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import java.time.LocalDateTime;

public record LetterAdminPageResponse(
    Long id,
    Long userId,
    Long petId,
    Long replyId,
    String email,
    Long count,
    String summary,
    String content,
    PromptType promptType,
    boolean inspection,
    LocalDateTime inspectionTime,
    ReplyStatus status,
    LocalDateTime submitTime,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
