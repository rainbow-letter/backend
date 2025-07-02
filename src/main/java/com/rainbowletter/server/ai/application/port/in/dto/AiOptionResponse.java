package com.rainbowletter.server.ai.application.port.in.dto;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import java.util.List;

public record AiOptionResponse(
    Long id,
    Long promptId,
    Integer maxTokens,
    Double temperature,
    Double topP,
    Double frequencyPenalty,
    Double presencePenalty,
    List<String> stop
) {

    public static AiOptionResponse from(final AiOption aiOption) {
        return new AiOptionResponse(
            aiOption.getId().value(),
            aiOption.getPromptId().value(),
            aiOption.getMaxTokens(),
            aiOption.getTemperature(),
            aiOption.getTopP(),
            aiOption.getFrequencyPenalty(),
            aiOption.getPresencePenalty(),
            aiOption.getStop()
        );
    }

}
