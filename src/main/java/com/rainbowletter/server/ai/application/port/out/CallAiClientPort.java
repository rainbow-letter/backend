package com.rainbowletter.server.ai.application.port.out;

import org.springframework.ai.chat.model.ChatResponse;

public interface CallAiClientPort {

    ChatResponse call(final AiClientCommand command);

}
