package com.rainbowletter.server.ai.adapter.in.web.dto;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;

public record UpdateAiConfigRequest(Boolean useABTest, PromptType selectPrompt) {

}
