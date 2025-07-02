package com.rainbowletter.server.reply.application.port.in.dto;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyReadStatus;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import java.time.LocalDateTime;

public record ReplyAdminResponse(
    Long id,
    Long petId,
    Long letterId,
    String summary,
    String content,
    String promptA,
    String promptB,
    PromptType promptType,
    boolean inspection,
    LocalDateTime inspectionTime,
    ReplyStatus status,
    LocalDateTime submitTime,
    ReplyReadStatus readStatus,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ReplyAdminResponse from(final Reply reply) {
        return new ReplyAdminResponse(
            reply.getId().value(),
            reply.getPetId().value(),
            reply.getLetterId().value(),
            reply.getSummary(),
            reply.getContent(),
            reply.getPromptA(),
            reply.getPromptB(),
            reply.getPromptType(),
            reply.isInspection(),
            reply.getInspectionTime(),
            reply.getStatus(),
            reply.getSubmitTime(),
            reply.getReadStatus(),
            reply.getCreatedAt(),
            reply.getUpdatedAt()
        );
    }

}
