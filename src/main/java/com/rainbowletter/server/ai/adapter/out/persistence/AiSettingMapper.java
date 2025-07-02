package com.rainbowletter.server.ai.adapter.out.persistence;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.domain.model.AiOption.AiOptionId;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiPromptId;
import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import com.rainbowletter.server.ai.application.domain.model.AiSetting.AiSettingId;
import com.rainbowletter.server.common.annotation.PersistenceMapper;
import java.util.List;
import java.util.Objects;

@PersistenceMapper
class AiSettingMapper {

    AiSetting mapToDomain(final AiConfigJpaEntity aiConfig, final List<AiPrompt> prompts) {
        return AiSetting.withId(
            new AiSettingId(aiConfig.getId()),
            aiConfig.getUseABTest(),
            aiConfig.getSelectPrompt(),
            prompts
        );
    }

    AiPrompt mapToPrompt(final AiPromptJpaEntity aiPrompt, final AiOptionJpaEntity aiOption) {
        return AiPrompt.withId(
            new AiPromptId(aiPrompt.getId()),
            new AiSettingId(aiPrompt.getConfigId()),
            aiPrompt.getProvider(),
            aiPrompt.getType(),
            aiPrompt.getModel(),
            aiPrompt.getSystem(),
            aiPrompt.getUser(),
            aiPrompt.getParameters(),
            mapToOption(aiOption)
        );
    }

    AiOption mapToOption(final AiOptionJpaEntity aiOption) {
        return AiOption.withId(
            new AiOptionId(aiOption.getId()),
            new AiPromptId(aiOption.getPromptId()),
            aiOption.getMaxTokens(),
            aiOption.getTemperature(),
            aiOption.getTopP(),
            aiOption.getFrequencyPenalty(),
            aiOption.getPresencePenalty(),
            aiOption.getStop()
        );
    }

    AiConfigJpaEntity mapToJpaEntity(final AiSetting domain) {
        return new AiConfigJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getUseABTest(),
            domain.getSelectPrompt()
        );
    }

    AiPromptJpaEntity mapToPromptEntity(final AiPrompt domain) {
        return new AiPromptJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getConfigId().value(),
            domain.getProvider(),
            domain.getType(),
            domain.getModel(),
            domain.getSystem(),
            domain.getUser(),
            domain.getParameters()
        );
    }

    AiOptionJpaEntity mapToOptionEntity(final AiOption domain) {
        return new AiOptionJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getPromptId().value(),
            domain.getMaxTokens(),
            domain.getTemperature(),
            domain.getTopP(),
            domain.getFrequencyPenalty(),
            domain.getPresencePenalty(),
            domain.getStop()
        );
    }

}
