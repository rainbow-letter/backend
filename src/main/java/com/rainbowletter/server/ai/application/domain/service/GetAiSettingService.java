package com.rainbowletter.server.ai.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import com.rainbowletter.server.ai.application.port.in.GetAiSettingUseCase;
import com.rainbowletter.server.ai.application.port.in.dto.AiSettingResponse;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetAiSettingService implements GetAiSettingUseCase {

    private final LoadSettingPort loadSettingPort;

    @Override
    public AiSettingResponse getSetting() {
        final AiSetting aiSetting = loadSettingPort.loadSetting();
        return AiSettingResponse.from(aiSetting);
    }

}
