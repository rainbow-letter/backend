package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;

public record PetInitiatedLetterUpdateRequest(
    @NotNull
    PromptType promptType,

    @NotNull
    String summary,

    @NotNull
    String content
) {
}
