package com.rainbowletter.server.ai.application.port.in;

import com.rainbowletter.server.ai.application.port.in.dto.AiSettingResponse;

public interface GetAiSettingUseCase {

    AiSettingResponse getSetting();

    AiSettingResponse getPetInitiatedLetterSetting();

}
