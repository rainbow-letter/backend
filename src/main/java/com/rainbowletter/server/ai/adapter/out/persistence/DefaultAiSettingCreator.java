package com.rainbowletter.server.ai.adapter.out.persistence;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.ai.application.domain.model.Parameter;
import com.rainbowletter.server.common.util.SystemEnvironment;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DefaultAiSettingCreator {

    private final SystemEnvironment environment;
    private final AiConfigJpaRepository aiConfigJpaRepository;
    private final AiPromptJpaRepository aiPromptJpaRepository;
    private final AiOptionJpaRepository aiOptionJpaRepository;

    @PostConstruct
    void initialize() {
        if (environment.isActiveTest() || aiConfigJpaRepository.existsById(1L)) {
            return;
        }

        final var aiConfig = new AiConfigJpaEntity(null, false, PromptType.A);
        aiConfigJpaRepository.save(aiConfig);

        final var promptA = new AiPromptJpaEntity(
            null,
            aiConfig.getId(),
            AiProvider.OPENAI,
            PromptType.A,
            "gpt-4o",
            "system",
            "사용자 %s님의 반려동물 %s",
            List.of(Parameter.PET_OWNER, Parameter.PET_NAME)
        );
        final var promptB = new AiPromptJpaEntity(
            null,
            aiConfig.getId(),
            AiProvider.OPENAI,
            PromptType.B,
            "gpt-4o",
            "system",
            "사용자 %s님의 반려동물 %s",
            List.of(Parameter.PET_OWNER, Parameter.PET_NAME)
        );
        aiPromptJpaRepository.saveAll(List.of(promptA, promptB));

        final var promptAOption = new AiOptionJpaEntity(
            null,
            promptA.getId(),
            1500,
            1.0,
            0.8,
            1.25,
            0.0,
            List.of("p.s")
        );
        final var promptBOption = new AiOptionJpaEntity(
            null,
            promptB.getId(),
            1500,
            1.0,
            0.8,
            1.25,
            0.0,
            List.of("p.s")
        );
        aiOptionJpaRepository.saveAll(List.of(promptAOption, promptBOption));
    }

}
