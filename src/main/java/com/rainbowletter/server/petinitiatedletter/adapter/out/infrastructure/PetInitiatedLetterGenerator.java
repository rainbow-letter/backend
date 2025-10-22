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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PetInitiatedLetterGenerator {

    private final LoadSettingPort loadSettingPort;
    private final CallAiClientPort callAiClientPort;

    public GeneratedLetterContent generate(Pet pet) {
        final AiSetting aiSetting = loadSettingPort.loadPetInitiatedLetterSetting();
        Pet.PetId petId = pet.getId();
        long threadId = Thread.currentThread().getId();

        log.info("[편지 작성 메소드 호출] petId={}, threadId={}", petId, threadId);

        if (Boolean.TRUE.equals(aiSetting.getUseABTest())) {
            final Map<PromptType, String> results = new EnumMap<>(PromptType.class);

            for (AiPrompt prompt : aiSetting.getPrompts()) {
                String content = callAiWithLogging(prompt, pet, petId, threadId);
                results.put(prompt.getType(), content);
            }

            PromptType selected = aiSetting.getSelectPrompt();
            String contentA = results.get(PromptType.A);
            String contentB = results.get(PromptType.B);
            String selectedContent = selected == PromptType.A ? contentA : contentB;

            return new GeneratedLetterContent(
                selectedContent.substring(0, 20),
                selectedContent,
                contentA,
                contentB,
                selected
            );

        } else {
            AiPrompt selectedPrompt = aiSetting.getSelectedPrompt();

            String content = callAiWithLogging(selectedPrompt, pet, petId, threadId);

            return new GeneratedLetterContent(
                content.substring(0, 20),
                content,
                selectedPrompt.getType() == PromptType.A ? content : "",
                selectedPrompt.getType() == PromptType.B ? content : "",
                selectedPrompt.getType()
            );
        }
    }

    private String callAiWithLogging(AiPrompt prompt, Pet pet, Pet.PetId petId, long threadId) {
        long start = System.currentTimeMillis();
        log.info("[OpenAI 호출 시작] petId={}, threadId={}, promptType={}, timestamp={}",
            petId, threadId, prompt.getType(), start);

        String content = callAiClientPort.call(new AiClientCommand(prompt, List.of(pet)))
            .getResult()
            .getOutput()
            .getContent();

        long end = System.currentTimeMillis();
        log.info("[OpenAI 응답 완료] petId={}, threadId={}, promptType={}, duration={}ms",
            petId, threadId, prompt.getType(), (end - start));

        return content;
    }
}