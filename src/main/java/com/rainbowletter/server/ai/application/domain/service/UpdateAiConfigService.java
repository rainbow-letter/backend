package com.rainbowletter.server.ai.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import com.rainbowletter.server.ai.application.port.in.UpdateAiConfigCommand;
import com.rainbowletter.server.ai.application.port.in.UpdateAiConfigUseCase;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.ai.application.port.out.UpdateSettingStatePort;
import com.rainbowletter.server.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateAiConfigService implements UpdateAiConfigUseCase {

    private final LoadSettingPort loadSettingPort;
    private final UpdateSettingStatePort updateSettingStatePort;

    @Override
    public void updateConfig(final UpdateAiConfigCommand command) {
        final AiSetting aiSetting = loadSettingPort.loadSetting();
        aiSetting.update(command.getUseABTest(), command.getSelectPrompt());
        updateSettingStatePort.updateConfig(aiSetting);
    }

}
