package com.rainbowletter.server.ai.adapter.in.web.dto;

import java.util.List;

public record UpdateAiOptionRequest(
    Integer maxTokens,
    Double temperature,
    Double topP,
    Double frequencyPenalty,
    Double presencePenalty,
    List<String> stop
) {

}
