package com.rainbowletter.server.ai.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.port.in.UpdateAiPromptCommand;
import com.rainbowletter.server.ai.application.port.in.UpdateAiPromptUseCase;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.ai.application.port.out.UpdateSettingStatePort;
import com.rainbowletter.server.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateAiPromptService implements UpdateAiPromptUseCase {

    private final LoadSettingPort loadSettingPort;
    private final UpdateSettingStatePort updateSettingStatePort;

    @Override
    public void updatePrompt(final UpdateAiPromptCommand command) {
        final AiPrompt aiPrompt = loadSettingPort.loadPrompt(command.getId());
        aiPrompt.update(
            command.getProvider(),
            command.getModel(),
            command.getSystem(),
            command.getUser(),
            command.getParameters()
        );
        updateSettingStatePort.updatePrompt(aiPrompt);
    }

}
