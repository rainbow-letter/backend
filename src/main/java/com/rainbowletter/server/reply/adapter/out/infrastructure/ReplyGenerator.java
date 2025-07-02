package com.rainbowletter.server.reply.adapter.out.infrastructure;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import com.rainbowletter.server.ai.application.port.out.AiClientCommand;
import com.rainbowletter.server.ai.application.port.out.CallAiClientPort;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.letter.adapter.out.dto.RecentLetterSummary;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyReadStatus;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyCommand;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
class ReplyGenerator implements GenerateReplyPort {

    private final LoadLetterPort loadLetterPort;
    private final LoadSettingPort loadSettingPort;
    private final CallAiClientPort callAiClientPort;

    @Override
    public Reply generate(final GenerateReplyCommand command) {
        final AiSetting aiSetting = loadSettingPort.loadSetting();
        if (Boolean.TRUE.equals(aiSetting.getUseABTest())) {
            final Map<PromptType, String> result = new EnumMap<>(PromptType.class);
            aiSetting.getPrompts()
                .forEach(prompt -> {
                    final var response = getResponseContent(prompt, command);
                    result.put(prompt.getType(), response);
                });
            return Reply.withoutId(
                command.getPet().getId(),
                command.getLetter().getId(),
                aiSetting.getSelectPrompt() == PromptType.A ? result.get(PromptType.A)
                    .substring(0, 20) : result.get(PromptType.B).substring(0, 20),
                aiSetting.getSelectPrompt() == PromptType.A ? result.get(PromptType.A)
                    : result.get(PromptType.B),
                result.get(PromptType.A),
                result.get(PromptType.B),
                aiSetting.getSelectPrompt(),
                true,
                LocalDateTime.now(),
                ReplyStatus.CHAT_GPT,
                null,
                ReplyReadStatus.UNREAD,
                LocalDateTime.now(),
                LocalDateTime.now()
            );
        } else {
            final AiPrompt prompt = aiSetting.getSelectedPrompt();
            final String response = getResponseContent(prompt, command);
            return Reply.withoutId(
                command.getPet().getId(),
                command.getLetter().getId(),
                response.substring(0, 20),
                response,
                prompt.getType() == PromptType.A ? response : "",
                prompt.getType() == PromptType.B ? response : "",
                aiSetting.getSelectPrompt(),
                true,
                LocalDateTime.now(),
                ReplyStatus.CHAT_GPT,
                null,
                ReplyReadStatus.UNREAD,
                LocalDateTime.now(),
                LocalDateTime.now()
            );
        }
    }

    private String getResponseContent(final AiPrompt aiPrompt, final GenerateReplyCommand command) {
        String dynamicSystem = buildDynamicSystemPrompt(aiPrompt.getSystem(), command.getPet());
        AiPrompt modifiedPrompt = aiPrompt.withSystem(dynamicSystem);

        List<Object> parameterInstances = List.of(
            command.getPet(),
            command.getLetter(),
            command.getLetterCount(),
            command.getIsFirstLetter()
        );

        List<RecentLetterSummary> recentLetters = loadLetterPort.loadRecentLettersByPetId(command.getPet().getId(), command.getLetter().getId().value());

        final AiClientCommand aiClientCommand = new AiClientCommand(modifiedPrompt, parameterInstances, recentLetters);

        log.info(aiClientCommand.toString());

        return callAiClientPort.call(aiClientCommand)
            .getResult()
            .getOutput()
            .getContent();
    }

    private String buildDynamicSystemPrompt(String baseSystem, Pet pet) {
        List<String> personalities = pet.getPersonalities();

        if (personalities == null || personalities.isEmpty()) {
            return baseSystem;
        }

        String joined = String.join(", ", personalities);
        return baseSystem + "\n\n" +
            pet.getName() + "은 " + joined + " 성격을 가지고 있어. 이런 성격이 드러나게끔 반영하여 답장을 써줘.";
    }


}
