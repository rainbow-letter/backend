package com.rainbowletter.server.ai.application.port.in.dto;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import java.util.List;

public record AiSettingResponse(AiConfigResponse config, List<AiPromptResponse> prompts) {

    public static AiSettingResponse from(final AiSetting aiSetting) {
        return new AiSettingResponse(
            AiConfigResponse.from(aiSetting),
            aiSetting.getPrompts()
                .stream()
                .map(AiPromptResponse::from)
                .toList()
        );
    }

    public record AiConfigResponse(Long id, boolean useABTest, PromptType selectPrompt) {

        public static AiConfigResponse from(final AiSetting config) {
            return new AiConfigResponse(
                config.getId().value(),
                config.getUseABTest(),
                config.getSelectPrompt()
            );
        }

    }

}
