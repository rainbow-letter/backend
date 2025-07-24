package com.rainbowletter.server.ai.adapter.out.persistence;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.domain.model.AiOption.AiOptionId;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiPromptId;
import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.ai.application.port.out.UpdateSettingStatePort;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@PersistenceAdapter
@RequiredArgsConstructor
class AiSettingPersistenceAdapter implements LoadSettingPort, UpdateSettingStatePort {

    private final AiSettingMapper aiSettingMapper;
    private final AiConfigJpaRepository aiConfigJpaRepository;
    private final AiPromptJpaRepository aiPromptJpaRepository;
    private final AiOptionJpaRepository aiOptionJpaRepository;

    @Override
    public AiSetting loadSetting() {
        final AiConfigJpaEntity aiConfig = aiConfigJpaRepository.findById(1L)
            .orElseThrow(() -> new RainbowLetterException("not.exists.ai.setting"));

        List<AiPrompt> prompts = Stream.of(1L, 2L)
            .map(id -> loadPrompt(new AiPromptId(id)))
            .toList();

        return aiSettingMapper.mapToDomain(aiConfig, prompts);
    }

    @Override
    public AiPrompt loadPrompt(final AiPromptId id) {
        final var aiPromptEntity = aiPromptJpaRepository.findById(id.value())
            .orElseThrow(() -> new RainbowLetterException("not.exists.ai.prompt"));
        final var aiOptionEntity = aiOptionJpaRepository.findByPromptId(aiPromptEntity.getId())
            .orElseThrow(() -> new RainbowLetterException("not.exists.ai.option"));
        return aiSettingMapper.mapToPrompt(aiPromptEntity, aiOptionEntity);
    }

    @Override
    public AiOption loadOption(final AiOptionId id) {
        final AiOptionJpaEntity aiOptionJpaEntity = aiOptionJpaRepository.findById(id.value())
            .orElseThrow(() -> new RainbowLetterException("not.exists.ai.option"));
        return aiSettingMapper.mapToOption(aiOptionJpaEntity);
    }

    @Override
    public void updateConfig(final AiSetting aiSetting) {
        final var mappedToJpaEntity = aiSettingMapper.mapToJpaEntity(aiSetting);
        aiConfigJpaRepository.save(mappedToJpaEntity);
    }

    @Override
    public void updatePrompt(final AiPrompt aiPrompt) {
        final var mappedToJpaEntity = aiSettingMapper.mapToPromptEntity(aiPrompt);
        aiPromptJpaRepository.save(mappedToJpaEntity);
    }

    @Override
    public void updateOption(final AiOption aiOption) {
        final var mappedToJpaEntity = aiSettingMapper.mapToOptionEntity(aiOption);
        aiOptionJpaRepository.save(mappedToJpaEntity);
    }

}
