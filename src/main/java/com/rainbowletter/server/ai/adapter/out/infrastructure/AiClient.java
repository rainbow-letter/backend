package com.rainbowletter.server.ai.adapter.out.infrastructure;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import com.rainbowletter.server.ai.application.port.out.AiClientCommand;
import org.springframework.ai.chat.model.ChatResponse;

interface AiClient {

    boolean support(AiProvider aiProvider);

    ChatResponse call(AiClientCommand command);

}
