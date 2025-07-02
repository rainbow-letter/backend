package com.rainbowletter.server.ai.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.ai.application.domain.model.AiOption.AiOptionId;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class UpdateAiOptionCommand {

    @NotNull
    AiOptionId id;

    @NotNull
    Integer maxTokens;

    @NotNull
    Double temperature;

    @NotNull
    Double topP;

    @NotNull
    Double frequencyPenalty;

    @NotNull
    Double presencePenalty;

    @NotNull
    List<String> stop;

    public UpdateAiOptionCommand(
        final AiOptionId id,
        final Integer maxTokens,
        final Double temperature,
        final Double topP,
        final Double frequencyPenalty,
        final Double presencePenalty,
        final List<String> stop
    ) {
        this.id = id;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.topP = topP;
        this.frequencyPenalty = frequencyPenalty;
        this.presencePenalty = presencePenalty;
        this.stop = stop;
        validate(this);
    }

}
