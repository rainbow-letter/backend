package com.rainbowletter.server.ai.application.domain.model;

import com.rainbowletter.server.ai.application.domain.model.AiSetting.AiSettingId;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AiPrompt {

    private final AiPromptId id;
    private final AiSettingId configId;

    private AiProvider provider;
    private PromptType type;
    private String model;
    private String system;
    private String user;
    private List<Parameter> parameters;
    private AiOption option;

    @SuppressWarnings("java:S107")
    public static AiPrompt withId(
        final AiPromptId id,
        final AiSettingId configId,
        final AiProvider provider,
        final PromptType type,
        final String model,
        final String system,
        final String user,
        final List<Parameter> parameters,
        final AiOption option
    ) {
        return new AiPrompt(id, configId, provider, type, model, system, user, parameters, option);
    }

    public AiPrompt withSystem(String newSystem) {
        return new AiPrompt(
            this.id,
            this.configId,
            this.provider,
            this.type,
            this.model,
            newSystem,
            this.user,
            this.parameters,
            this.option
        );
    }

    public void update(
        final AiProvider provider,
        final String model,
        final String system,
        final String user,
        final List<Parameter> parameters
    ) {
        validateUserPromptParameters(user, parameters);
        this.provider = provider;
        this.model = model;
        this.system = system;
        this.user = user;
        this.parameters = parameters;
    }

    private void validateUserPromptParameters(
        final String userPrompt,
        final List<Parameter> parameters
    ) {
        final int totalLength = userPrompt.length();
        final int replacedLength = userPrompt.replace("%s", "%").length();
        final int parameterSize = totalLength - replacedLength;
        if (parameterSize != parameters.size()) {
            throw new RainbowLetterException("not.match.ai.parameter.size");
        }
    }

    public enum AiProvider {OPENAI, GEMINI}

    public enum PromptType {A, B}

    public record AiPromptId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
