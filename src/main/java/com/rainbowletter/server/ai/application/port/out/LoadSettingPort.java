package com.rainbowletter.server.ai.application.port.out;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.domain.model.AiOption.AiOptionId;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiPromptId;
import com.rainbowletter.server.ai.application.domain.model.AiSetting;

public interface LoadSettingPort {

    AiSetting loadSetting();

    AiSetting loadPetInitiatedLetterSetting();

    AiPrompt loadPrompt(AiPromptId id);

    AiOption loadOption(AiOptionId id);

}
