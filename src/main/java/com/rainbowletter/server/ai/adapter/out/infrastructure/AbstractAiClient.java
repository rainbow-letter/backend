package com.rainbowletter.server.ai.adapter.out.infrastructure;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.port.out.AiClientCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;

@RequiredArgsConstructor
abstract class AbstractAiClient implements AiClient {

    private final ChatModel chatModel;

    @Override
    public ChatResponse call(final AiClientCommand command) {
        List<Message> chatMessages = convertToChatMessages(command.getMessages());
        final ChatOptions chatOptions = createChatOptions(command.getModel(), command.getOption());
        final Prompt prompt = new Prompt(chatMessages, chatOptions);
        return chatModel.call(prompt);
    }

    protected abstract ChatOptions createChatOptions(String model, AiOption aiOption);

    private List<Message> convertToChatMessages(List<AiClientCommand.Message> messages) {
        return messages.stream()
            .map(m -> switch (m.getRole()) {
                case SYSTEM -> new SystemMessage(m.getContent());
                case USER -> new UserMessage(m.getContent());
                case ASSISTANT -> new AssistantMessage(m.getContent());
            })
            .map(msg -> (Message) msg)
            .toList();
    }
}
