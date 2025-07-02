package com.rainbowletter.server.reply.adapter.in.web.dto;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;

public record ReplyUpdateRequest(
    PromptType promptType,
    String summary,
    String content
) {

}
