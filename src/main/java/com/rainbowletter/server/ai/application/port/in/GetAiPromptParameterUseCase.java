package com.rainbowletter.server.ai.application.port.in;

import com.rainbowletter.server.ai.application.port.in.dto.AiPromptParameterResponse;

public interface GetAiPromptParameterUseCase {

    AiPromptParameterResponse getPromptParameters();

}
