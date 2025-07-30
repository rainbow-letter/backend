package com.rainbowletter.server.petinitiatedletter.application.port.in.dto;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;

public record GeneratedLetterContent(
    String summary,
    String content,
    String promptA,
    String promptB,
    PromptType selectedPrompt
) {
}
