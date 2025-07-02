package com.rainbowletter.server.common.config;

import com.google.cloud.vertexai.VertexAI;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class AiConfig {

    @Value("${openai.token}")
    private String openAIToken;

    @Value("${gemini.project-id}")
    private String geminiProjectId;

    @Value("${gemini.location}")
    private String geminiLocation;

    @Bean
    ChatModel openAiChatModel() {
        final OpenAiApi openAiApi = new OpenAiApi(openAIToken);
        return new OpenAiChatModel(openAiApi);
    }

    @Bean
    ChatModel geminiChatModel() {
        final VertexAI vertexAI = new VertexAI(geminiProjectId, geminiLocation);
        return new VertexAiGeminiChatModel(vertexAI);
    }

}
