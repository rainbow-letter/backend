package com.rainbowletter.server.ai.application.domain.model;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiPromptId;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PROTECTED)
public class AiOption {

    private final AiOptionId id;
    private final AiPromptId promptId;

    private Integer maxTokens;
    private Double temperature;
    private Double topP;
    private Double frequencyPenalty;
    private Double presencePenalty;
    private List<String> stop;

    @SuppressWarnings("java:S107")
    public static AiOption withId(
        final AiOptionId id,
        final AiPromptId promptId,
        final Integer maxTokens,
        final Double temperature,
        final Double topP,
        final Double frequencyPenalty,
        final Double presencePenalty,
        final List<String> stop
    ) {
        return new AiOption(
            id,
            promptId,
            maxTokens,
            temperature,
            topP,
            frequencyPenalty,
            presencePenalty,
            stop
        );
    }

    public void update(
        final Integer maxTokens,
        final Double temperature,
        final Double topP,
        final Double frequencyPenalty,
        final Double presencePenalty,
        final List<String> stop
    ) {
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.topP = topP;
        this.frequencyPenalty = frequencyPenalty;
        this.presencePenalty = presencePenalty;
        this.stop = stop;
    }

    public record AiOptionId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
