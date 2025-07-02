package com.rainbowletter.server.ai.application.port.out;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import com.rainbowletter.server.ai.application.domain.model.Parameter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.letter.adapter.out.dto.RecentLetterSummary;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static com.rainbowletter.server.ai.application.port.out.AiClientCommand.ChatRole.SYSTEM;
import static com.rainbowletter.server.ai.application.port.out.AiClientCommand.ChatRole.USER;

@Value
public class AiClientCommand {

    String model;
    AiProvider provider;
    List<Message> messages;
    AiOption option;

    public AiClientCommand(final AiPrompt aiPrompt, final List<Object> parameterInstances, final List<RecentLetterSummary> recentLetters) {
        this.model = aiPrompt.getModel();
        this.provider = aiPrompt.getProvider();
        this.option = aiPrompt.getOption();
        this.messages = buildMessages(aiPrompt, parameterInstances, recentLetters);
    }

    private List<Message> buildMessages(
        final AiPrompt aiPrompt,
        final List<Object> parameterInstances,
        final List<RecentLetterSummary> recentLetters
    ) {
        List<Message> messages = new ArrayList<>();

        // 시스템 프롬프트 추가
        messages.add(new Message(SYSTEM, aiPrompt.getSystem()));

        // 이전 대화 기록 추가 (네이밍 포함)
        int index = 1;
        for (RecentLetterSummary letter : recentLetters) {
            if (letter.letterContent() != null) {
                messages.add(new Message(USER, "Previous letter " + index++ + ": " + letter.letterContent()));
            }
        }

        // 마지막 메시지 - 현재 편지 내용
        final String currentPrompt = combineUserParameters(
            aiPrompt.getUser(),
            aiPrompt.getParameters(),
            parameterInstances
        );
        messages.add(new Message(USER, currentPrompt));

        return messages;
    }

    private String combineUserParameters(
        final String userPrompt,
        final List<Parameter> parameters,
        final List<Object> parameterInstances
    ) {
        final ArrayList<String> formatValues = new ArrayList<>();
        for (final Parameter parameter : parameters) {
            final String value = parameter.value(
                parameterInstances.stream()
                    .filter(parameterInstance -> parameter.getClazz().isInstance(parameterInstance))
                    .findAny()
                    .orElseThrow(() -> new RainbowLetterException("Ai 파라미터 인스턴스가 존재하지 않습니다."))
            );
            formatValues.add(value);
        }
        return String.format(userPrompt, formatValues.toArray());
    }

    @Value
    public static class Message {
        ChatRole role;
        String content;
    }

    public enum ChatRole {
        SYSTEM,
        USER,
        ASSISTANT
    }

}
