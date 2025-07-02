package com.rainbowletter.server.ai.adapter.out.infrastructure;

import com.rainbowletter.server.ai.application.port.out.AiClientCommand;
import com.rainbowletter.server.ai.application.port.out.CallAiClientPort;
import com.rainbowletter.server.common.annotation.InfraAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;

@InfraAdapter
@RequiredArgsConstructor
class AiClientAdapter implements CallAiClientPort {

    private final List<AiClient> aiClients;

    public ChatResponse call(final AiClientCommand command) {
        final AiClient aiClient = aiClients.stream()
            .filter(client -> client.support(command.getProvider()))
            .findAny()
            .orElseThrow(() -> new RainbowLetterException("지원하는 AI를 찾을 수 없습니다."));
        return aiClient.call(command);
    }

}
