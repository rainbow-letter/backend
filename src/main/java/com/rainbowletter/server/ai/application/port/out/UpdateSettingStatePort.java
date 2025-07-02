package com.rainbowletter.server.ai.application.port.out;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiSetting;

public interface UpdateSettingStatePort {

    void updateConfig(AiSetting aiSetting);

    void updatePrompt(AiPrompt aiPrompt);

    void updateOption(AiOption aiOption);

}
