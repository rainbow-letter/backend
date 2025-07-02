package com.rainbowletter.server.ai.adapter.out.infrastructure;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
class OpenAiClient extends AbstractAiClient {

    OpenAiClient(@Qualifier("openAiChatModel") final ChatModel openAiChatModel) {
        super(openAiChatModel);
    }

    @Override
    public boolean support(final AiProvider aiProvider) {
        return AiProvider.OPENAI.equals(aiProvider);
    }

    @Override
    protected ChatOptions createChatOptions(final String model, final AiOption aiOption) {
        return OpenAiChatOptions.builder()
            .withModel(model)
            .withMaxTokens(aiOption.getMaxTokens())
            .withTemperature(aiOption.getTemperature())
            .withTopP(aiOption.getTopP())
            .withFrequencyPenalty(aiOption.getFrequencyPenalty())
            .withPresencePenalty(aiOption.getPresencePenalty())
            .withStop(aiOption.getStop())
            .build();
    }

}
