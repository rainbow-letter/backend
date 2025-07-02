package com.rainbowletter.server.ai.adapter.in.web;

import com.rainbowletter.server.ai.adapter.in.web.dto.UpdateAiPromptRequest;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiPromptId;
import com.rainbowletter.server.ai.application.port.in.UpdateAiPromptCommand;
import com.rainbowletter.server.ai.application.port.in.UpdateAiPromptUseCase;
import com.rainbowletter.server.common.annotation.WebAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/ai")
@Tag(name = "ai")
class UpdateAiPromptController {

    private final UpdateAiPromptUseCase updateAiPromptUseCase;

    @PutMapping("/prompts/{id}")
    void updatePrompt(
        @PathVariable("id") final Long id,
        @RequestBody final UpdateAiPromptRequest request
    ) {
        final var command = new UpdateAiPromptCommand(
            new AiPromptId(id),
            request.provider(),
            request.model(),
            request.system(),
            request.user(),
            request.parameters()
        );
        updateAiPromptUseCase.updatePrompt(command);
    }

}
