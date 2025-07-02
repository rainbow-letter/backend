package com.rainbowletter.server.ai.adapter.in.web.dto;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;

import com.rainbowletter.server.ai.application.domain.model.Parameter;
import java.util.List;

public record UpdateAiPromptRequest(
    AiProvider provider,
    String model,
    String system,
    String user,
    List<Parameter> parameters
) {

}
