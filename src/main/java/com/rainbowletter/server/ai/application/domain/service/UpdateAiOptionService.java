package com.rainbowletter.server.ai.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.port.in.UpdateAiOptionCommand;
import com.rainbowletter.server.ai.application.port.in.UpdateAiOptionUseCase;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.ai.application.port.out.UpdateSettingStatePort;
import com.rainbowletter.server.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateAiOptionService implements UpdateAiOptionUseCase {

    private final LoadSettingPort loadSettingPort;
    private final UpdateSettingStatePort updateSettingStatePort;

    @Override
    public void updateOption(final UpdateAiOptionCommand command) {
        final AiOption aiOption = loadSettingPort.loadOption(command.getId());
        aiOption.update(
            command.getMaxTokens(),
            command.getTemperature(),
            command.getTopP(),
            command.getFrequencyPenalty(),
            command.getPresencePenalty(),
            command.getStop()
        );
        updateSettingStatePort.updateOption(aiOption);
    }

}
