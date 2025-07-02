package com.rainbowletter.server.ai.application.port.in;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import static com.rainbowletter.server.common.util.Validation.validate;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class UpdateAiConfigCommand {

    @NotNull
    Boolean useABTest;

    @NotNull
    PromptType selectPrompt;

    public UpdateAiConfigCommand(final Boolean useABTest, final PromptType selectPrompt) {
        this.useABTest = useABTest;
        this.selectPrompt = selectPrompt;
        validate(this);
    }

}
