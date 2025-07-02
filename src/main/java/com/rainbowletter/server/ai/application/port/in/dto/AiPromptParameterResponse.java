package com.rainbowletter.server.ai.application.port.in.dto;

import com.rainbowletter.server.ai.application.domain.model.Parameter;
import java.util.List;

public record AiPromptParameterResponse(List<Parameter> parameters) {

    public static AiPromptParameterResponse from(final List<Parameter> parameters) {
        return new AiPromptParameterResponse(parameters);
    }

}
