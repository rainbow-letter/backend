package com.rainbowletter.server.ai.application.port.in;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiPromptId;
import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.ai.application.domain.model.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class UpdateAiPromptCommand {

    @NotNull
    AiPromptId id;

    @NotNull
    AiProvider provider;

    @NotBlank
    String model;

    @NotBlank
    String system;

    @NotBlank
    String user;

    @NotNull
    List<Parameter> parameters;

    public UpdateAiPromptCommand(
        final AiPromptId id,
        final AiProvider provider,
        final String model,
        final String system,
        final String user,
        final List<Parameter> parameters
    ) {
        this.id = id;
        this.provider = provider;
        this.model = model;
        this.system = system;
        this.user = user;
        this.parameters = parameters;
        validate(this);
    }

}
