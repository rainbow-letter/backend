package com.rainbowletter.server.ai.adapter.out.infrastructure;

import com.rainbowletter.server.ai.application.domain.model.AiOption;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
class GeminiAiClient extends AbstractAiClient {

    GeminiAiClient(@Qualifier("geminiChatModel") final ChatModel geminiChatModel) {
        super(geminiChatModel);
    }

    @Override
    public boolean support(final AiProvider aiProvider) {
        return AiProvider.GEMINI.equals(aiProvider);
    }

    @Override
    protected ChatOptions createChatOptions(final String model, final AiOption aiOption) {
        return VertexAiGeminiChatOptions.builder()
            .withModel(model)
            .withMaxOutputTokens(aiOption.getMaxTokens())
            .withTemperature(aiOption.getTemperature())
            .withTopP(aiOption.getTopP())
            .withStopSequences(aiOption.getStop())
            .build();
    }

}
