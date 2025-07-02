package com.rainbowletter.server.ai.application.domain.model;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AiSetting {

    private final AiSettingId id;

    private Boolean useABTest;
    private PromptType selectPrompt;
    private List<AiPrompt> prompts;

    public static AiSetting withId(
        final AiSettingId id,
        final Boolean useABTest,
        final PromptType selectPrompt,
        final List<AiPrompt> prompts
    ) {
        return new AiSetting(id, useABTest, selectPrompt, prompts);
    }

    public AiPrompt getSelectedPrompt() {
        return prompts.stream()
            .filter(prompt -> prompt.getType().equals(selectPrompt))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
    }

    public void update(final Boolean useABTest, final PromptType selectPrompt) {
        this.useABTest = useABTest;
        this.selectPrompt = selectPrompt;
    }

    public record AiSettingId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
