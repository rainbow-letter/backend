package com.rainbowletter.server.ai.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.Parameter;
import com.rainbowletter.server.ai.application.port.in.GetAiPromptParameterUseCase;
import com.rainbowletter.server.ai.application.port.in.dto.AiPromptParameterResponse;
import com.rainbowletter.server.common.annotation.UseCase;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetAiPromptParameterService implements GetAiPromptParameterUseCase {

    @Override
    public AiPromptParameterResponse getPromptParameters() {
        return AiPromptParameterResponse.from(
            Arrays.stream(Parameter.values())
                .toList()
        );
    }

}
