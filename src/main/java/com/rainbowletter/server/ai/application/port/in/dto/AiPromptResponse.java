package com.rainbowletter.server.ai.application.port.in.dto;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.ai.application.domain.model.Parameter;
import java.util.List;

public record AiPromptResponse(
    Long id,
    Long configId,
    AiProvider provider,
    PromptType type,
    String model,
    String system,
    String user,
    List<Parameter> parameters,
    AiOptionResponse option
) {

    public static AiPromptResponse from(final AiPrompt aiPrompt) {
        return new AiPromptResponse(
            aiPrompt.getId().value(),
            aiPrompt.getConfigId().value(),
            aiPrompt.getProvider(),
            aiPrompt.getType(),
            aiPrompt.getModel(),
            aiPrompt.getSystem(),
            aiPrompt.getUser(),
            aiPrompt.getParameters(),
            AiOptionResponse.from(aiPrompt.getOption())
        );
    }

}
