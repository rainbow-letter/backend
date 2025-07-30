package com.rainbowletter.server.petinitiatedletter.adapter.out.infrastructure;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import com.rainbowletter.server.ai.application.port.out.AiClientCommand;
import com.rainbowletter.server.ai.application.port.out.CallAiClientPort;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.GeneratedLetterContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PetInitiatedLetterGenerator {

    private final LoadSettingPort loadSettingPort;
    private final CallAiClientPort callAiClientPort;

    public GeneratedLetterContent generate(Pet pet) {
        final AiSetting aiSetting = loadSettingPort.loadSetting();

        if (Boolean.TRUE.equals(aiSetting.getUseABTest())) {
            final Map<PromptType, String> results = new EnumMap<>(PromptType.class);
            aiSetting.getPrompts().forEach(prompt -> {
                final String systemPrompt = buildDynamicSystemPrompt(prompt.getSystem(), pet);
                final AiPrompt modifiedPrompt = prompt.withSystem(systemPrompt);

                final AiClientCommand command = new AiClientCommand(modifiedPrompt, List.of(pet), List.of());
                final String content = callAiClientPort.call(command)
                    .getResult()
                    .getOutput()
                    .getContent();

                results.put(prompt.getType(), content);
            });

            final PromptType selected = aiSetting.getSelectPrompt();
            return new GeneratedLetterContent(
                selected == PromptType.A ? results.get(PromptType.A).substring(0, 20) : results.get(PromptType.B).substring(0, 20),
                selected == PromptType.A ? results.get(PromptType.A) : results.get(PromptType.B),
                results.get(PromptType.A),
                results.get(PromptType.B),
                selected
            );
        } else {
            final AiPrompt selectedPrompt = aiSetting.getSelectedPrompt();
            final String systemPrompt = buildDynamicSystemPrompt(selectedPrompt.getSystem(), pet);
            final AiPrompt modifiedPrompt = selectedPrompt.withSystem(systemPrompt);

            final AiClientCommand command = new AiClientCommand(modifiedPrompt, List.of(pet), List.of());
            final String content = callAiClientPort.call(command)
                .getResult()
                .getOutput()
                .getContent();

            return new GeneratedLetterContent(
                content.substring(0, 20),
                content,
                selectedPrompt.getType() == PromptType.A ? content : "",
                selectedPrompt.getType() == PromptType.B ? content : "",
                selectedPrompt.getType()
            );
        }
    }

    private String buildDynamicSystemPrompt(String baseSystem, Pet pet) {
        List<String> personalities = pet.getPersonalities();

        if (personalities == null || personalities.isEmpty()) {
            return baseSystem;
        }

        String joined = String.join(", ", personalities);
        return baseSystem + "\n\n" +
            pet.getName() + "은 " + joined + " 성격을 가지고 있어. 이런 성격이 드러나게끔 편지를 작성해줘.";
    }

}
